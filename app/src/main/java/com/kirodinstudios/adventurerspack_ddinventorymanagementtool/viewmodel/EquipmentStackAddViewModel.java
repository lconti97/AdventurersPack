package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.BasicApp;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackDao;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class EquipmentStackAddViewModel extends AndroidViewModel {
    private AppDatabase appDatabase;
    private EquipmentStackDao equipmentStackDao;
    private Context context;

    public EquipmentStackAddViewModel(@NonNull Application application) {
        super(application);

        context = application.getApplicationContext();
        appDatabase = ((BasicApp) application).getDatabase();
        equipmentStackDao = appDatabase.equipmentStackDao();
    }

    public void addEquipmentStack(EquipmentStack equipmentStack) {
        Callable addEquipmentStackCallable = () -> {
            equipmentStackDao.insert(equipmentStack);
            return null;
        };
        Callable failureCallable = () -> {
            String failureMessageTemplate = context.getResources().getString(R.string.equipment_stack_add_failure_message);
            String failureMessage = String.format(failureMessageTemplate, equipmentStack.getName());
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
            return null;
        };

        appDatabase.executeQuery(addEquipmentStackCallable, failureCallable);
    }
}
