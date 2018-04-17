package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.BasicApp;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import java.util.List;


public class EquipmentStackListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<EquipmentStackEntity>> mObservableEquipmentStacks;

    public EquipmentStackListViewModel(@NonNull Application application) {
        super(application);

        mObservableEquipmentStacks = new MediatorLiveData<>();
        mObservableEquipmentStacks.setValue(null);

        LiveData<List<EquipmentStackEntity>> equipmentStacks = ((BasicApp) application).getRepository()
                .getEquipmentStacks();

        mObservableEquipmentStacks.addSource(equipmentStacks, mObservableEquipmentStacks::setValue);
    }

    public LiveData<List<EquipmentStackEntity>> getEquipmentStacks() {
        return mObservableEquipmentStacks;
    }
}
