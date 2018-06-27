package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<EquipmentStackEntity>> mObservableEquipmentStacks;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableEquipmentStacks = new MediatorLiveData<>();

        mObservableEquipmentStacks.addSource(mDatabase.equipmentStackDao().loadAll(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableEquipmentStacks.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<EquipmentStackEntity>> getEquipmentStacks() {
        return mObservableEquipmentStacks;
    }

    public LiveData<EquipmentStackEntity> getEquipmentStack(final int id) {
        return mDatabase.equipmentStackDao().loadEquipmentStack(id);
    }
}
