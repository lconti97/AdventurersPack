package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplateEntity;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.WeaponTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.WeaponTemplateEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
abstract class EquipmentTemplateDao {
    @Query("SELECT * FROM EquipmentTemplate " +
            "WHERE equipmentTemplateId NOT IN (SELECT equipmentTemplateId FROM ArmorTemplateEntity)")
    abstract LiveData<List<EquipmentTemplate>> getAllEquipmentTemplates();

    @Query("SELECT * FROM ArmorTemplateEntity " +
            "INNER JOIN EquipmentTemplate ON ArmorTemplateEntity.armorTemplateId=EquipmentTemplate.equipmentTemplateId")
    abstract LiveData<List<ArmorTemplate>> getAllArmorTemplates();

    @Query("SELECT * FROM WeaponTemplateEntity " +
            "INNER JOIN EquipmentTemplate ON WeaponTemplateEntity.weaponTemplateId=EquipmentTemplate.equipmentTemplateId")
    abstract LiveData<List<WeaponTemplate>> getAllWeaponTemplates();

    @Transaction
    Long insertArmorTemplate(ArmorTemplate armorTemplate) {
        Long id = insertEquipmentTemplate(armorTemplate);
        ArmorTemplateEntity armorTemplateEntity = new ArmorTemplateEntity(
                id,
                armorTemplate.getArmorClass(),
                armorTemplate.getArmorCategory(),
                armorTemplate.getGivesDisadvantageOnStealthChecks(),
                armorTemplate.getRequiresMinimumStrength(),
                armorTemplate.getMinimumStrength()
        );
        return insertArmorTemplateEntity(armorTemplateEntity);
    }

    @Transaction
    Long insertWeaponTemplate(WeaponTemplate weaponTemplate) {
        Long id = insertEquipmentTemplate(weaponTemplate);
        WeaponTemplateEntity weaponTemplateEntity = new WeaponTemplateEntity(
                id,
                weaponTemplate.getDamage(),
                weaponTemplate.getProperties(),
                weaponTemplate.getIsSimpleWeapon(),
                weaponTemplate.getIsMeleeWeapon()
        );
        return insertWeaponTemplateEntity(weaponTemplateEntity);
    }

    @Insert
    abstract Long insertEquipmentTemplate(EquipmentTemplate equipmentTemplate);

    @Insert
    abstract Long insertArmorTemplateEntity(ArmorTemplateEntity armorTemplateEntity);

    @Insert
    abstract Long insertWeaponTemplateEntity(WeaponTemplateEntity weaponTemplateEntity);
}
