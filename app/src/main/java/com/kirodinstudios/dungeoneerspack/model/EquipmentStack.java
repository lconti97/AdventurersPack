package com.kirodinstudios.dungeoneerspack.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class EquipmentStack {
    @PrimaryKey(autoGenerate = true)
    private int equipmentStackId;
    private int count;
    @Embedded
    private EquipmentTemplate equipmentTemplate;

    public EquipmentStack() { }

    @Ignore
    public EquipmentStack(int id, int count, EquipmentTemplate equipmentTemplate) {
        this.equipmentStackId = id;
        this.count = count;
        this.equipmentTemplate = equipmentTemplate;
    }

    public int getEquipmentStackId() {
        return equipmentStackId;
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

    public EquipmentTemplate getEquipmentTemplate() {
        return equipmentTemplate;
    }

    public void setEquipmentTemplate(EquipmentTemplate equipmentTemplateId) {
        this.equipmentTemplate = equipmentTemplateId;
    }
}
