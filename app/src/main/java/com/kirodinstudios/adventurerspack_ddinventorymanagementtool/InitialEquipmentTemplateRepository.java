package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.content.Context;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.io.ByteStreams.toByteArray;

public class InitialEquipmentTemplateRepository {
    private static final String ARMOR_TEMPLATES_FILE_NAME = "ArmorTemplates.json";
    private static final String WEAPON_TEMPLATES_FILE_NAME = "WeaponTemplates.json";

    private Gson gson;

    public InitialEquipmentTemplateRepository() {
        gson = new Gson();
    }

    public ArrayList<EquipmentTemplate> getInitialEquipmentTemplates(Context context) {
        ArrayList<EquipmentTemplate> equipmentTemplates = new ArrayList<>();

        try {
            InputStream armorInputStream = context.getAssets().open(ARMOR_TEMPLATES_FILE_NAME);
            String armorJson = new String(toByteArray(armorInputStream));
            Type armorCollectionType = new TypeToken<Collection<ArmorTemplate>>(){}.getType();
            Collection<ArmorTemplate> armorTemplates = gson.fromJson(armorJson, armorCollectionType);
            equipmentTemplates.addAll(armorTemplates);
        } catch (IOException e) {
            Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
        }

        return equipmentTemplates;
    }
}
