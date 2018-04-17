package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.AppExecutors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {EquipmentStackEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    @VisibleForTesting
    public static final String DATABASE_NAME = "adventurers-pack";

    private static AppDatabase Instance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public synchronized static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = buildDatabase(context.getApplicationContext(), executors);
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
                            List<EquipmentStackEntity> equipmentStackEntities = getPopulationEquipmentStacks();
                            database.equipmentStackDao().insertAll(equipmentStackEntities);
                            database.setDatabaseCreated();
                        });
                    }
                })
                .build();
    }

    private static List<EquipmentStackEntity> getPopulationEquipmentStacks() {
        return Arrays.asList(
                new EquipmentStackEntity("sword", 1),
                new EquipmentStackEntity("gold", 200)
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
}
