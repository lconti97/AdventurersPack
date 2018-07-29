package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.BasicApp;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackDao;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentTemplateDao;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EquipmentStackAddViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private EquipmentStackDao equipmentStackDao;
    private EquipmentTemplateDao equipmentTemplateDao;
    private Context context;
    private LiveData<List<EquipmentTemplate>> equipmentTemplates;

    public EquipmentStackAddViewModel(@NonNull Application application) {
        super(application);

        context = application.getApplicationContext();
        appDatabase = ((BasicApp) application).getDatabase();
        equipmentStackDao = appDatabase.equipmentStackDao();
        equipmentTemplateDao = appDatabase.equipmentTemplateDao();

        equipmentTemplates = equipmentTemplateDao.getAllTemplates();
    }

    public LiveData<List<EquipmentTemplate>> getAllEquipmentTemplates() {
        return equipmentTemplates;
    }

    public void addEquipmentTemplateAndEquipmentStack(EquipmentTemplate equipmentTemplate, EquipmentStack equipmentStack) {
        Callable<Void> addEquipmentTemplateCallable = () -> {
            // TODO: null checks
            long equipmentTemplateId = equipmentTemplateDao.insertTemplate(equipmentTemplate);
            equipmentStack.setEquipmentTemplateId(equipmentTemplateId);
            equipmentStackDao.insertEquipmentStack(equipmentStack);
            return null;
        };
        Callable<Void> failureCallable = () -> {
            String failureMessageTemplate = context.getResources().getString(R.string.equipment_template_add_failure_message);
            String failureMessage = String.format(failureMessageTemplate, equipmentTemplate.getName());
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
            return null;
        };

        appDatabase.executeQuery(addEquipmentTemplateCallable, failureCallable);
    }

    public void addEquipmentStack(EquipmentStack equipmentStack) {
        Callable<Void> addEquipmentStackCallable = () -> {
            equipmentStackDao.insertAll(Arrays.asList(equipmentStack));
            return null;
        };
        Callable<Void> failureCallable = () -> {
            String failureMessageTemplate = context.getResources().getString(R.string.equipment_stack_add_failure_message);
            String failureMessage = String.format(failureMessageTemplate, equipmentStack.getName());
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
            return null;
        };

        appDatabase.executeQuery(addEquipmentStackCallable, failureCallable);
    }
}
