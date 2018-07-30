package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.io.ByteStreams.toByteArray;

public class InitialEquipmentTemplateRepository {
    private static final String ARMOR_TEMPLATES_FILE_NAME = "ArmorTemplates.json";
    private static final String WEAPON_TEMPLATES_FILE_NAME = "WeaponTemplates.json";

    private ObjectMapper objectMapper;

    public InitialEquipmentTemplateRepository() {
        objectMapper = new ObjectMapper();
    }

    public ArrayList<ArmorTemplate> getInitialEquipmentTemplates(Context context) {
        ArrayList<ArmorTemplate> equipmentTemplates = new ArrayList<>();

        try {
            InputStream armorInputStream = context.getAssets().open(ARMOR_TEMPLATES_FILE_NAME);
            String armorJson = new String(toByteArray(armorInputStream));
            Collection<ArmorTemplate> armorTemplates = objectMapper.readValue(armorJson, new TypeReference<Collection<ArmorTemplate>>() { });
            equipmentTemplates.addAll(armorTemplates);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
        }

        return equipmentTemplates;
    }
}
