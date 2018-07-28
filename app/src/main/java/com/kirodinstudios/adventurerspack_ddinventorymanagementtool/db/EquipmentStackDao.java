package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.Collection;
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

    @Query("SELECT * FROM EquipmentStack WHERE equipmentStackId = :id")
    LiveData<EquipmentStack> loadEquipmentStack(int id);

    @Insert()
    void insertAll(Collection<EquipmentStack> equipmentStacks);

    @Delete
    void delete(EquipmentStack equipmentStack);
}
