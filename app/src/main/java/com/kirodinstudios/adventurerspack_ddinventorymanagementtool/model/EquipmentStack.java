package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "EquipmentStack",
        foreignKeys = @ForeignKey(
                entity = EquipmentTemplate.class,
                parentColumns = "equipmentTemplateId",
                childColumns = "equipmentTemplateId"))
public class EquipmentStack {
    @PrimaryKey(autoGenerate = true)
    private int equipmentStackId;

    private String name;
    private int count;
    private long equipmentTemplateId;

    public EquipmentStack(String name, int count, long equipmentTemplateId) {
        this.name = name;
        this.count = count;
        this.equipmentTemplateId = equipmentTemplateId;
    }

    public int getEquipmentStackId() {
        return equipmentStackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEquipmentStackId(int equipmentStackId) {
        this.equipmentStackId = equipmentStackId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getEquipmentTemplateId() {
        return equipmentTemplateId;
    }

    public void setEquipmentTemplateId(long equipmentTemplateId) {
        this.equipmentTemplateId = equipmentTemplateId;
    }
}
