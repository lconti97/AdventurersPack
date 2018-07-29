package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface EquipmentTemplateDao {
    @Query("SELECT * FROM EquipmentTemplate")
    LiveData<List<EquipmentTemplate>> getAllTemplates();

    @Insert
    void insertAllTemplates(Collection<EquipmentTemplate> equipmentTemplate);

    @Insert
    long insertTemplate(EquipmentTemplate equipmentTemplate);
}
