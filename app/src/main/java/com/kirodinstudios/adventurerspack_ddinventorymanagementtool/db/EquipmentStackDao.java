package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EquipmentStackDao {
    @Query("SELECT * FROM EquipmentStack")
    LiveData<List<EquipmentStack>> loadAll();

    @Query("SELECT * FROM EquipmentStack WHERE id = :id")
    LiveData<EquipmentStack> loadEquipmentStack(int id);

    @Insert
    void insert(EquipmentTemplate equipmentTemplate);

    @Insert()
    void insert(EquipmentStack equipmentStack, EquipmentTemplate equipmentTemplate);

    @Delete
    void delete(EquipmentStack equipmentStack);
}
