package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

@Entity(tableName = "equipment_stacks")
public class EquipmentStackEntity implements EquipmentStack {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int count;

    public EquipmentStackEntity(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
