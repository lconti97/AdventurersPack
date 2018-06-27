package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EquipmentStackDao {
    @Query("SELECT * FROM equipment_stacks")
    LiveData<List<EquipmentStackEntity>> loadAll();

    @Query("SELECT * FROM equipment_stacks WHERE id = :id")
    LiveData<EquipmentStackEntity> loadEquipmentStack(int id);

    @Insert
    void insertAll(List<EquipmentStackEntity> equipmentStackEntities);

    @Delete
    void delete(EquipmentStackEntity equipmentStackEntity);
}
