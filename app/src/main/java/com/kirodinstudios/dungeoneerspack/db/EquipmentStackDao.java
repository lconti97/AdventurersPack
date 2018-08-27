package com.kirodinstudios.dungeoneerspack.db;

import com.kirodinstudios.dungeoneerspack.model.EquipmentStack;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
abstract class EquipmentStackDao {
    @Query("SELECT * FROM EquipmentStack " +
            "INNER JOIN EquipmentTemplate ON EquipmentStack.equipmentTemplateId=EquipmentTemplate.equipmentTemplateId")
    abstract LiveData<List<EquipmentStack>> loadAll();

    @Query("SELECT * FROM EquipmentStack " +
            "INNER JOIN EquipmentTemplate ON EquipmentStack.equipmentTemplateId=EquipmentTemplate.equipmentTemplateId " +
            "WHERE equipmentStackId = :id")
    abstract LiveData<EquipmentStack> loadEquipmentStack(int id);

    @Transaction
    void insertAll(List<EquipmentStack> equipmentStacks) {
        for (EquipmentStack equipmentStack: equipmentStacks) {
            insertEquipmentStack(equipmentStack);
        }
    }

    @Insert
    abstract Long insertEquipmentStack(EquipmentStack equipmentStackEntity);
}
