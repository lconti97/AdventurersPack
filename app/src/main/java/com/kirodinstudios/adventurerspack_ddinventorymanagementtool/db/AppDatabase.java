package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import android.content.Context;
import android.util.Log;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.AppExecutors;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.InitialEquipmentTemplateRepository;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.kirodinstudios.adventurerspack_ddinventorymanagementtool.Constants.LOG_TAG;

@Database(entities = {EquipmentStack.class, EquipmentTemplate.class, ArmorTemplate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    @VisibleForTesting
    public static final String DATABASE_NAME = "adventurers-pack";
    private static final Callable<Void> DEFAULT_FAILURE_CALLABLE = () -> null;

    abstract EquipmentStackDao equipmentStackDao();
    abstract EquipmentTemplateDao equipmentTemplateDao();
    abstract ArmorTemplateDao armorTemplateDao();

    private static AppDatabase Instance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private Executor databaseThread;

    public synchronized static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = buildDatabase(context.getApplicationContext(), executors);
                    Instance.databaseThread = executors.diskIO();
                    Instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return Instance;
    }

    public LiveData<List<EquipmentTemplate>> getEquipmentTemplates() {
        MediatorLiveData<List<EquipmentTemplate>> liveData = new MediatorLiveData<>();

        liveData.addSource(equipmentTemplateDao().getAllTemplates(), equipmentTemplates ->
                addEquipmentTemplatesToLiveData(equipmentTemplates, liveData));
        liveData.addSource(armorTemplateDao().getAllTemplates(), armorTemplates ->
                addEquipmentTemplatesToLiveData(new ArrayList<>(armorTemplates), liveData));

        return liveData;
    }

    public LiveData<List<EquipmentStack>> getEquipmentStacks() {
        return equipmentStackDao().loadAll();
    }

    public LiveData<EquipmentStack> getEquipmentStack(int id) {
        return equipmentStackDao().loadEquipmentStack(id);
    }

    private Long insertEquipmentTemplateInForeground(EquipmentTemplate equipmentTemplate) {
        if (equipmentTemplate.getClass() == ArmorTemplate.class)
            return armorTemplateDao().insertTemplate((ArmorTemplate) equipmentTemplate);
        else if (equipmentTemplate.getClass() == EquipmentTemplate.class)
            return equipmentTemplateDao().insertTemplate(equipmentTemplate);

        return null;
    }

    public void insertEquipmentTemplateAndEquipmentStackInBackground(EquipmentTemplate equipmentTemplate, EquipmentStack equipmentStack) {
        Callable<Void> addEquipmentTemplateCallable = () -> {
            Long equipmentTemplateId = insertEquipmentTemplateInForeground(equipmentTemplate);

            if (equipmentTemplateId == null) {
                Log.e(LOG_TAG, "Failed to save EquipmentTemplate: " + equipmentTemplate.toString());
            } else {
                equipmentStack.setEquipmentTemplateId(equipmentTemplateId);
                equipmentStackDao().insertEquipmentStack(equipmentStack);
            }

            return null;
        };

        executeQuery(addEquipmentTemplateCallable);
    }

    public void insertEquipmentTemplatesInBackground(List<EquipmentTemplate> equipmentTemplates) {
        Callable<Void> callable = () -> {
            equipmentTemplateDao().insertAllTemplates(equipmentTemplates);
            return null;
        };

        executeQuery(callable);
    }

    public void insertEquipmentStackInBackground(EquipmentStack equipmentStack) {
        Callable<Void> callable = () -> {
            equipmentStackDao().insertEquipmentStack(equipmentStack);
            return null;
        };

        executeQuery(callable);
    }

    private static void addEquipmentTemplatesToLiveData(List<EquipmentTemplate> equipmentTemplates, MediatorLiveData<List<EquipmentTemplate>> liveData) {
        if (liveData.getValue() == null)
            liveData.setValue(equipmentTemplates);
        else
            liveData.getValue().addAll(equipmentTemplates);
    }

    private static AppDatabase buildDatabase(final Context context, final AppExecutors executors) {
        AppDatabase appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
            .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadScheduledExecutor().execute(() -> {
                        AppDatabase database = AppDatabase.getInstance(context, executors);

                        InitialEquipmentTemplateRepository initialEquipmentTemplateRepository = new InitialEquipmentTemplateRepository();
                        List<EquipmentTemplate> equipmentTemplates = initialEquipmentTemplateRepository.getInitialEquipmentTemplates(context);

                        database.insertEquipmentTemplatesInBackground(equipmentTemplates);

                        database.setDatabaseCreated();
                    });
                }
            })
            .build();
        return appDatabase;
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void executeQuery(Callable queryFunction) {
        databaseThread.execute(() -> {
            try {
                queryFunction.call();
            } catch (Exception e) {
                Log.e(LOG_TAG, Log.getStackTraceString(e));
                try {
                    ((Callable) AppDatabase.DEFAULT_FAILURE_CALLABLE).call();
                } catch (Exception e1) {
                    Log.e(LOG_TAG, Log.getStackTraceString(e));
                }
            }
        });
    }
}
