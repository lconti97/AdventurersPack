package com.kirodinstudios.dungeoneerspack.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ArmorTemplateAdditionalFields {
    @PrimaryKey(autoGenerate = true)
    private Long armorTemplateAdditionalFieldsId;
    private String armorClass;
    private String armorCategory;
    private Boolean givesDisadvantageOnStealthChecks;
    private Boolean requiresMinimumStrength;
    private Integer minimumStrength;

    public ArmorTemplateAdditionalFields() { }

    @Ignore
    public ArmorTemplateAdditionalFields(
            String armorClass,
            String armorCategory,
            Boolean givesDisadvantageOnStealthChecks,
            Boolean requiresMinimumStrength,
            Integer minimumStrength) {

        this.armorClass = armorClass;
        this.armorCategory = armorCategory;
        this.givesDisadvantageOnStealthChecks = givesDisadvantageOnStealthChecks;
        this.requiresMinimumStrength = requiresMinimumStrength;
        this.minimumStrength = minimumStrength;
    }

    public Long getArmorTemplateAdditionalFieldsId() {
        return armorTemplateAdditionalFieldsId;
    }

    public void setArmorTemplateAdditionalFieldsId(Long armorTemplateAdditionalFieldsId) {
        this.armorTemplateAdditionalFieldsId = armorTemplateAdditionalFieldsId;
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

    public Integer getMinimumStrength() {
        return minimumStrength;
    }

    public void setMinimumStrength(Integer minimumStrength) {
        this.minimumStrength = minimumStrength;
    }
}
