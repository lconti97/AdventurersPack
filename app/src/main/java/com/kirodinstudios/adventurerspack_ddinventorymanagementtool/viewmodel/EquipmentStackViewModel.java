package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.BasicApp;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.DataRepository;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;


public class EquipmentStackViewModel extends AndroidViewModel {

    private final LiveData<EquipmentStackEntity> mObservableEquipmentStack;
    private final int mEquipmentStackId;
    public ObservableField<EquipmentStackEntity> equipmentStack = new ObservableField<>();

    public EquipmentStackViewModel(@NonNull Application application, DataRepository repository,
                                   final int equipmentStackId) {
        super(application);
        mEquipmentStackId = equipmentStackId;

        mObservableEquipmentStack = repository.getEquipmentStack(equipmentStackId);
    }

    public LiveData<EquipmentStackEntity> getObservableEquipmentStack() {
        return mObservableEquipmentStack;
    }

    public void setEquipmentStack(EquipmentStackEntity equipmentStack) {
        this.equipmentStack.set(equipmentStack);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        private final int mEquipmentStackId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int equipmentStackId) {
            mApplication = application;
            mEquipmentStackId = equipmentStackId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new EquipmentStackViewModel(mApplication, mRepository, mEquipmentStackId);
        }
    }
}
