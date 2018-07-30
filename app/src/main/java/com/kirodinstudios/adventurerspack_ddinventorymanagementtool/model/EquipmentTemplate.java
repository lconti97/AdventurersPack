package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EquipmentTemplate")
public class EquipmentTemplate {
    @PrimaryKey(autoGenerate = true)
    private long equipmentTemplateId;
    private String name;
    private String description;
    private String equipmentType;
    private Double costInGp;
    private Double weightInPounds;

    public EquipmentTemplate() { }

    public EquipmentTemplate(
            String name,
            String description,
            String equipmentType,
            Double costInGp,
            Double weightInPounds) {
        this.name = name;
        this.description = description;
        this.equipmentType = equipmentType;
        this.costInGp = costInGp;
        this.weightInPounds = weightInPounds;
    }

    public long getEquipmentTemplateId() {
        return equipmentTemplateId;
    }

    public void setEquipmentTemplateId(long id) {
        this.equipmentTemplateId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
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
}
