package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

public class EquipmentTypes {
    public static final String WEAPON = "Weapon";
    public static final String OTHER = "Other";
    public static final String ARMOR = "Armor";

    public static final String[] EQUIPMENT_TYPES = {
            WEAPON,
            OTHER,
            ARMOR
    };

    public static String getTypeStringFromClass(Class<? extends EquipmentTemplate> equipmentTemplateClass) {
        if (equipmentTemplateClass == WeaponTemplate.class)
            return WEAPON;
        else if (equipmentTemplateClass == ArmorTemplate.class)
            return ARMOR;
        else
            return OTHER;
    }
}
