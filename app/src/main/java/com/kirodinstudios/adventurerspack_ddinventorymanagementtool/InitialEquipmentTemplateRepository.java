package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.content.Context;

import com.google.gson.Gson;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.ArrayList;

public class InitialEquipmentTemplateRepository {
    private static final String ARMOR_TEMPLATES_FILE_NAME = "ArmorTemplates.json";
    private static final String WEAPON_TEMPLATES_FILE_NAME = "WeaponTemplates.json";

    private Gson gson;

    public InitialEquipmentTemplateRepository() {
        gson = new Gson();
    }

    public ArrayList<EquipmentTemplate> getInitialEquipmentTemplates(Context context) {
        ArrayList<EquipmentTemplate> equipmentTemplates = new ArrayList<>();

        equipmentTemplates.add(new ArmorTemplate("Template 1", "description",
                3.0, 1.0, "13", "Light",
                false, false, 2));
        equipmentTemplates.add(new EquipmentTemplate("Template 2", "description", "Weapon", 3.0, 1.0));

//        try {
//            InputStream armorInputStream = context.getAssets().open(ARMOR_TEMPLATES_FILE_NAME);
//            String armorJson = new String(toByteArray(armorInputStream));
//            Type armorCollectionType = new TypeToken<Collection<ArmorTemplate>>(){}.getType();
//            Collection<ArmorTemplate> armorTemplates = gson.fromJson(armorJson, armorCollectionType);
//            equipmentTemplates.addAll(armorTemplates);
//        } catch (IOException e) {
//            Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
//        }

        return equipmentTemplates;
    }
}
