package com.kirodinstudios.dungeoneerspack;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirodinstudios.dungeoneerspack.model.ArmorTemplateAdditionalFields;
import com.kirodinstudios.dungeoneerspack.model.EquipmentTemplate;
import com.kirodinstudios.dungeoneerspack.model.EquipmentTypes;
import com.kirodinstudios.dungeoneerspack.model.WeaponTemplateAdditionalFields;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.io.ByteStreams.toByteArray;

public class InitialEquipmentTemplateRepository {
    private static final String ARMOR_TEMPLATES_FILE_NAME = "ArmorTemplates.json";
    private static final String WEAPON_TEMPLATES_FILE_NAME = "WeaponTemplates.json";

    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COST = "costInGp";
    private static final String KEY_WEIGHT = "weightInPounds";
    private static final String KEY_ARMOR_CLASS = "armorClass";
    private static final String KEY_ARMOR_CATEGORY = "armorCategory";
    private static final String KEY_ARMOR_DISADVANTAGE_STEALTH = "givesDisadvantageOnStealthChecks";
    private static final String KEY_ARMOR_REQUIRES_MINIMUM_STRENGTH = "requiresMinimumStrength";
    private static final String KEY_MINIMUM_STRENGTH = "minimumStrength";
    private static final String KEY_WEAPON_PROPERTIES = "properties";
    private static final String KEY_WEAPON_IS_SIMPLE = "isSimpleWeapon";
    private static final String KEY_WEAPON_IS_MELEE = "isMeleeWeapon";
    private static final String KEY_WEAPON_DAMAGE = "damage";

    private ObjectMapper objectMapper;

    public InitialEquipmentTemplateRepository() {
        objectMapper = new ObjectMapper();
    }

    public ArrayList<EquipmentTemplate> getInitialEquipmentTemplates(Context context) {
        ArrayList<EquipmentTemplate> equipmentTemplates = new ArrayList<>();

        try {
            addAllTemplatesFromArmorFile(equipmentTemplates, ARMOR_TEMPLATES_FILE_NAME, context);
            addAllTemplatesFromArmorFile(equipmentTemplates, WEAPON_TEMPLATES_FILE_NAME, context);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
        }

        return equipmentTemplates;
    }

    private void addAllTemplatesFromArmorFile(
            List<EquipmentTemplate> equipmentTemplates,
            String fileName,
            Context context) throws Exception {

        InputStream inputStream = context.getAssets().open(fileName);
        String equipmentJson = new String(toByteArray(inputStream));

        Collection<Map<String, Object>> equipmentTemplateMaps = objectMapper.readValue(
                equipmentJson,
                new TypeReference<Collection<Map<String, Object>>>(){});

        for (Map<String, Object> equipmentTemplateMap: equipmentTemplateMaps) {

            EquipmentTemplate equipmentTemplate = getEquipmentTemplateFromMap(equipmentTemplateMap);

            if (fileName.equals(ARMOR_TEMPLATES_FILE_NAME)) {

                ArmorTemplateAdditionalFields armorTemplateAdditionalFields =
                        getArmorTemplateAdditionalFieldsFromMap(equipmentTemplateMap);

                equipmentTemplate.setArmorTemplateAdditionalFields(armorTemplateAdditionalFields);
                equipmentTemplate.setType(EquipmentTypes.ARMOR);
            }
            else if (fileName.equals(WEAPON_TEMPLATES_FILE_NAME)) {

                WeaponTemplateAdditionalFields  weaponTemplateAdditionalFields =
                        getWeaponTemplateAdditionalFieldsFromMap(equipmentTemplateMap);

                equipmentTemplate.setWeaponTemplateAdditionalFields(weaponTemplateAdditionalFields);
                equipmentTemplate.setType(EquipmentTypes.WEAPON);
            }

            equipmentTemplates.add(equipmentTemplate);
        }
    }

    private EquipmentTemplate getEquipmentTemplateFromMap(Map<String, Object> equipmentTemplateMap) {
        String name = (String) equipmentTemplateMap.get(KEY_NAME);
        String description = (String) equipmentTemplateMap.get(KEY_DESCRIPTION);
        Double costInGp = Double.valueOf(equipmentTemplateMap.get(KEY_COST).toString());
        Double weightInPounds = Double.valueOf(equipmentTemplateMap.get(KEY_WEIGHT).toString());

        return new EquipmentTemplate(
                name,
                description,
                costInGp,
                weightInPounds,
                EquipmentTypes.OTHER,
                null,
                null);
    }

    private ArmorTemplateAdditionalFields getArmorTemplateAdditionalFieldsFromMap(Map<String, Object> equipmentTemplateMap) {
        String armorClass = equipmentTemplateMap.get(KEY_ARMOR_CLASS).toString();
        String armorCategory = (String) equipmentTemplateMap.get(KEY_ARMOR_CATEGORY);
        Boolean givesDisadvantageOnStealthChecks = (Boolean) equipmentTemplateMap.get(KEY_ARMOR_DISADVANTAGE_STEALTH);
        Boolean requiresMinimumStrength = (Boolean) equipmentTemplateMap.get(KEY_ARMOR_REQUIRES_MINIMUM_STRENGTH);
        Integer minimumStrength = (Integer) equipmentTemplateMap.get(KEY_MINIMUM_STRENGTH);

        return new ArmorTemplateAdditionalFields(
                armorClass,
                armorCategory,
                givesDisadvantageOnStealthChecks,
                requiresMinimumStrength,
                minimumStrength);
    }

    private WeaponTemplateAdditionalFields getWeaponTemplateAdditionalFieldsFromMap(Map<String, Object> equipmentTemplateMap) {
        String properties = (String) equipmentTemplateMap.get(KEY_WEAPON_PROPERTIES);
        String damage = (String) equipmentTemplateMap.get(KEY_WEAPON_DAMAGE);
        Boolean isSimpleWeapon = (Boolean) equipmentTemplateMap.get(KEY_WEAPON_IS_SIMPLE);
        Boolean isMeleeWeapon = (Boolean) equipmentTemplateMap.get(KEY_WEAPON_IS_MELEE);

        return new WeaponTemplateAdditionalFields(
                damage,
                properties,
                isSimpleWeapon,
                isMeleeWeapon);
    }
}
