package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.WeaponTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.google.common.io.ByteStreams.toByteArray;

public class InitialEquipmentTemplateRepository {
    private static final String ARMOR_TEMPLATES_FILE_NAME = "ArmorTemplates.json";
    private static final String WEAPON_TEMPLATES_FILE_NAME = "WeaponTemplates.json";

    private ObjectMapper objectMapper;

    public InitialEquipmentTemplateRepository() {
        objectMapper = new ObjectMapper();
    }

    public ArrayList<EquipmentTemplate> getInitialEquipmentTemplates(Context context) {
        ArrayList<EquipmentTemplate> equipmentTemplates = new ArrayList<>();

        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            JavaType armorType = typeFactory.constructCollectionType(Collection.class, ArmorTemplate.class);
            JavaType weaponType = typeFactory.constructCollectionType(Collection.class, WeaponTemplate.class);

            addAllTemplatesFromFile(equipmentTemplates, ARMOR_TEMPLATES_FILE_NAME, armorType, context);
            addAllTemplatesFromFile(equipmentTemplates, WEAPON_TEMPLATES_FILE_NAME, weaponType, context);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
        }

        return equipmentTemplates;
    }

    private void addAllTemplatesFromFile(
            List<EquipmentTemplate> equipmentTemplates,
            String fileName,
            JavaType javaType,
            Context context) throws Exception {

        InputStream inputStream = context.getAssets().open(fileName);
        String armorJson = new String(toByteArray(inputStream));
        Collection<EquipmentTemplate> templatesToAdd = objectMapper.readValue(armorJson, javaType);
        equipmentTemplates.addAll(templatesToAdd);
    }
}
