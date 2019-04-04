package com.kirodinstudios.dungeoneerspack.db;

import com.kirodinstudios.dungeoneerspack.model.EquipmentTemplate;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
abstract class EquipmentTemplateDao {
    @Query("SELECT * FROM EquipmentTemplate")
    abstract LiveData<List<EquipmentTemplate>> getAllEquipmentTemplates();

    @Insert
    abstract Long insertEquipmentTemplate(EquipmentTemplate equipmentTemplate);
}
