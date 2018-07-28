package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.Collection;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface EquipmentTemplateDao {
    @Insert
    void insertAllTemplates(Collection<EquipmentTemplate> equipmentTemplate);
}
