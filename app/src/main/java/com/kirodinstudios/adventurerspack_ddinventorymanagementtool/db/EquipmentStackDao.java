package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
