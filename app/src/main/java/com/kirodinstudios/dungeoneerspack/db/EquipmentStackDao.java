package com.kirodinstudios.dungeoneerspack.db;

import com.kirodinstudios.dungeoneerspack.model.EquipmentStack;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface EquipmentStackDao {
    @Query("SELECT * FROM EquipmentStack")
    LiveData<List<EquipmentStack>> loadAll();

    @Query("SELECT * FROM EquipmentStack WHERE equipmentStackId = :id")
    LiveData<EquipmentStack> loadEquipmentStack(int id);

    @Insert()
    void insertAll(List<EquipmentStack> equipmentStacks);

    @Insert
    Long insertEquipmentStack(EquipmentStack equipmentStack);

    @Delete
    void delete(EquipmentStack equipmentStack);
}
