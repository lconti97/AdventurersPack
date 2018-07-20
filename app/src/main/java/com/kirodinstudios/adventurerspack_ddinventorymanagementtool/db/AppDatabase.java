package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import android.content.Context;
import android.util.Log;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.AppExecutors;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.InitialEquipmentTemplateRepository;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {EquipmentStack.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    @VisibleForTesting
    public static final String DATABASE_NAME = "adventurers-pack";
    private static final String LOG_TAG = "AppDatabase";

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

    private static AppDatabase buildDatabase(final Context context, final AppExecutors executors) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(context, executors);
                            List<EquipmentStack> equipmentStackEntities = getPopulationEquipmentStacks();
                            InitialEquipmentTemplateRepository initialEquipmentTemplateRepository = new InitialEquipmentTemplateRepository();
                            Collection<EquipmentTemplate> equipmentTemplates = initialEquipmentTemplateRepository.getInitialEquipmentTemplates(context);
                            database.equipmentStackDao().insertAll(equipmentStackEntities);
                            database.setDatabaseCreated();
                        });
                    }
                })
                .build();
    }

    private static List<EquipmentStack> getPopulationEquipmentStacks() {
        return Arrays.asList(
                new EquipmentStack("sword", 1),
                new EquipmentStack("gold", 200)
        );
    }

    public abstract EquipmentStackDao equipmentStackDao();

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

    public void executeQuery(Callable queryFunction, Callable onFailure) {
        databaseThread.execute(() -> {
            try {
                queryFunction.call();
            } catch (Exception e) {
                Log.e(LOG_TAG, Log.getStackTraceString(e));
                try {
                    onFailure.call();
                } catch (Exception e1) {
                    Log.e(LOG_TAG, Log.getStackTraceString(e));
                }
            }
        });
    }
}
