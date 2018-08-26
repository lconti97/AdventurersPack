package com.kirodinstudios.dungeoneerspack.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class ArmorTemplateEntity {
    @PrimaryKey
    @ForeignKey(entity = EquipmentTemplate.class,
            parentColumns = "equipmentTemplateId",
            childColumns = "armorTemplateId")
    public Long armorTemplateId;
    public String armorClass;
    public String armorCategory;
    public Boolean givesDisadvantageOnStealthChecks;
    public Boolean requiresMinimumStrength;
    public Integer minimumStrength;

    public ArmorTemplateEntity(
            Long armorTemplateId,
            String armorClass,
            String armorCategory,
            Boolean givesDisadvantageOnStealthChecks,
            Boolean requiresMinimumStrength,
            Integer minimumStrength) {
        this.armorTemplateId = armorTemplateId;
        this.armorClass = armorClass;
        this.armorCategory = armorCategory;
        this.givesDisadvantageOnStealthChecks = givesDisadvantageOnStealthChecks;
        this.requiresMinimumStrength = requiresMinimumStrength;
        this.minimumStrength = minimumStrength;
    }
}
