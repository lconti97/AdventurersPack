package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "EquipmentStack")
public class EquipmentStack {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int count;

    public EquipmentStack() { }

    public EquipmentStack(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
