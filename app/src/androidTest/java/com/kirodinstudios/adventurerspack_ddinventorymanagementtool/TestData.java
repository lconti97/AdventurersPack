package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.Arrays;
import java.util.List;

public class TestData {
    private static final EquipmentStack EQUIPMENT_STACK_1 = new EquipmentStack(1, 4);
    private static final EquipmentStack EQUIPMENT_STACK_2 = new EquipmentStack(2, 4);

    public static final List<EquipmentStack> EQUIPMENT_STACKS = Arrays.asList(EQUIPMENT_STACK_1, EQUIPMENT_STACK_2);
}
