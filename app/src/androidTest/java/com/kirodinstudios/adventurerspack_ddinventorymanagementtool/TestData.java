package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import java.util.Arrays;
import java.util.List;

public class TestData {
    private static final EquipmentStackEntity EQUIPMENT_STACK_1 = new EquipmentStackEntity("sword", 1);
    private static final EquipmentStackEntity EQUIPMENT_STACK_2 = new EquipmentStackEntity("gold", 2);

    static final List<EquipmentStackEntity> EQUIPMENT_STACKS = Arrays.asList(EQUIPMENT_STACK_1, EQUIPMENT_STACK_2);
}
