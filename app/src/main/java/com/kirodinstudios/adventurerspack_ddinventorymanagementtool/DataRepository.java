package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.Collection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<Collection<EquipmentStack>> mObservableEquipmentStacks;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableEquipmentStacks = new MediatorLiveData<>();

        mObservableEquipmentStacks.addSource(mDatabase.getEquipmentStacks(),
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
    public LiveData<Collection<EquipmentStack>> getEquipmentStacks() {
        return mObservableEquipmentStacks;
    }

    public LiveData<EquipmentStack> getEquipmentStack(final int id) {
        return mDatabase.getEquipmentStack(id);
    }
}
