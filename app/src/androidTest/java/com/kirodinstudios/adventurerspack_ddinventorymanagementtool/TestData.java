package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import java.util.Arrays;
import java.util.List;

public class TestData {
    static final EquipmentStackEntity EQUIPMENT_STACK = new EquipmentStackEntity("sword", 1);
    static final EquipmentStackEntity EQUIPMENT_STACK2 = new EquipmentStackEntity("gold", 2);

    static final List<EquipmentStackEntity> EQUIPMENT_STACKS = Arrays.asList(EQUIPMENT_STACK, EQUIPMENT_STACK2);
}
