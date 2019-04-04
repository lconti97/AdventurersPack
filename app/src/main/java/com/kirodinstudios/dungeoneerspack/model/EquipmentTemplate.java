package com.kirodinstudios.dungeoneerspack.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EquipmentTemplate")
public class EquipmentTemplate {
    @PrimaryKey(autoGenerate = true)
    private Long equipmentTemplateId;
    private String name;
    private String description;
    private Double costInGp;
    private Double weightInPounds;
    private String type;
    @Embedded
    private ArmorTemplateAdditionalFields armorTemplateAdditionalFields;
    @Embedded
    private WeaponTemplateAdditionalFields weaponTemplateAdditionalFields;

    public EquipmentTemplate() { }

    public EquipmentTemplate(
            String name,
            String description,
            Double costInGp,
            Double weightInPounds,
            String type,
            ArmorTemplateAdditionalFields armorTemplateAdditionalFields,
            WeaponTemplateAdditionalFields weaponTemplateAdditionalFields) {
        this.name = name;
        this.description = description;
        this.costInGp = costInGp;
        this.weightInPounds = weightInPounds;
        this.type = type;
        this.armorTemplateAdditionalFields = armorTemplateAdditionalFields;
        this.weaponTemplateAdditionalFields = weaponTemplateAdditionalFields;
    }

    public Long getEquipmentTemplateId() {
        return equipmentTemplateId;
    }

    public void setEquipmentTemplateId(Long id) {
        this.equipmentTemplateId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCostInGp() {
        return costInGp;
    }

    public void setCostInGp(Double costInGp) {
        this.costInGp = costInGp;
    }

    public Double getWeightInPounds() {
        return weightInPounds;
    }

    public void setWeightInPounds(Double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArmorTemplateAdditionalFields getArmorTemplateAdditionalFields() {
        return armorTemplateAdditionalFields;
    }

    public void setArmorTemplateAdditionalFields(ArmorTemplateAdditionalFields armorTemplateAdditionalFields) {
        this.armorTemplateAdditionalFields = armorTemplateAdditionalFields;
    }

    public WeaponTemplateAdditionalFields getWeaponTemplateAdditionalFields() {
        return weaponTemplateAdditionalFields;
    }

    public void setWeaponTemplateAdditionalFields(WeaponTemplateAdditionalFields weaponTemplateAdditionalFields) {
        this.weaponTemplateAdditionalFields = weaponTemplateAdditionalFields;
    }

}
