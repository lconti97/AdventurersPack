package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.PrimaryKey;

public class EquipmentTemplate {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String equipmentType;

    public EquipmentTemplate(String name, String equipmentType) {
        this.name = name;
        this.equipmentType = equipmentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "";
    }
}
