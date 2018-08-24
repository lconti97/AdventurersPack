package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class WeaponTemplateEntity {

    @PrimaryKey
    @ForeignKey(entity = EquipmentTemplate.class,
            parentColumns = "equipmentTemplateId",
            childColumns = "weaponTemplateId")
    public Long weaponTemplateId;
    public String damage;
    public String properties;
    public Boolean isSimpleWeapon;
    public Boolean isMeleeWeapon;

    public WeaponTemplateEntity(Long weaponTemplateId, String damage, String properties, Boolean isSimpleWeapon, Boolean isMeleeWeapon) {
        this.weaponTemplateId = weaponTemplateId;
        this.damage = damage;
        this.properties = properties;
        this.isSimpleWeapon = isSimpleWeapon;
        this.isMeleeWeapon = isMeleeWeapon;
    }
}
