package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

public class EquipmentStackModel {
    private int id;
    private String name;
    private int count;
    private EquipmentTemplate equipmentTemplate;

    public EquipmentStackModel(String name, int count, EquipmentTemplate equipmentTemplate) {
        this.name = name;
        this.count = count;
        this.equipmentTemplate = equipmentTemplate;
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
