package com.kirodinstudios.dungeoneerspack.model;

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

    public EquipmentTemplate() { }

    public EquipmentTemplate(
            String name,
            String description,
            Double costInGp,
            Double weightInPounds) {
        this.name = name;
        this.description = description;
        this.costInGp = costInGp;
        this.weightInPounds = weightInPounds;
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
}
