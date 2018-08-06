package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel;

import android.app.Application;
import android.content.Context;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.BasicApp;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EquipmentStackAddViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private Context context;
    private LiveData<List<EquipmentTemplate>> equipmentTemplates;

    public EquipmentStackAddViewModel(@NonNull Application application) {
        super(application);

        context = application.getApplicationContext();
        appDatabase = ((BasicApp) application).getDatabase();

        equipmentTemplates = appDatabase.getEquipmentTemplates();
    }

    public LiveData<List<EquipmentTemplate>> getAllEquipmentTemplates() {
        return appDatabase.getEquipmentTemplates();
    }

    public void addEquipmentTemplateAndEquipmentStack(EquipmentTemplate equipmentTemplate, EquipmentStack equipmentStack) {
        appDatabase.insertEquipmentTemplateAndEquipmentStackInBackground(equipmentTemplate, equipmentStack);
    }

    public void addEquipmentStack(EquipmentStack equipmentStack) {
        appDatabase.insertEquipmentStackInBackground(equipmentStack);
    }
}
