package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "ArmorTemplate")
public class ArmorTemplate extends EquipmentTemplate {
    //TODO: duplicated from class annotation? Also change that var name

    @ForeignKey(entity = EquipmentTemplate.class, parentColumns = "equipmentTemplateId", childColumns = "parentTemplateId")
    private long parentTemplateId;
    private String armorClass;
    private String armorCategory;
    private Boolean givesDisadvantageOnStealthChecks;
    private Boolean requiresMinimumStrength;
    private Integer minimumStrength;
    public ArmorTemplate() { }

    public ArmorTemplate(
            String name,
            String description,
            Double costInGp,
            Double weightInPounds,
            String armorClass,
            String armorCategory,
            Boolean givesDisadvantageOnStealthChecks,
            Boolean requiresMinimumStrength,
            Integer minimumStrength) {

        super(name, description, EquipmentTypes.ARMOR, costInGp, weightInPounds);
        this.armorClass = armorClass;
        this.armorCategory = armorCategory;
        this.givesDisadvantageOnStealthChecks = givesDisadvantageOnStealthChecks;
        this.requiresMinimumStrength = requiresMinimumStrength;
        this.minimumStrength = minimumStrength;
    }

    public String getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(String armorClass) {
        this.armorClass = armorClass;
    }

    public String getArmorCategory() {
        return armorCategory;
    }

    public void setArmorCategory(String armorCategory) {
        this.armorCategory = armorCategory;
    }

    public Boolean getGivesDisadvantageOnStealthChecks() {
        return givesDisadvantageOnStealthChecks;
    }

    public void setGivesDisadvantageOnStealthChecks(Boolean givesDisadvantageOnStealthChecks) {
        this.givesDisadvantageOnStealthChecks = givesDisadvantageOnStealthChecks;
    }

    public Boolean getRequiresMinimumStrength() {
        return requiresMinimumStrength;
    }

    public void setRequiresMinimumStrength(Boolean requiresMinimumStrength) {
        this.requiresMinimumStrength = requiresMinimumStrength;
    }

    public long getParentTemplateId() {
        return parentTemplateId;
    }

    public void setParentTemplateId(long parentTemplateId) {
        this.parentTemplateId = parentTemplateId;
    }

    public Integer getMinimumStrength() {
        return minimumStrength;
    }

    public void setMinimumStrength(Integer minimumStrength) {
        this.minimumStrength = minimumStrength;
    }
}
