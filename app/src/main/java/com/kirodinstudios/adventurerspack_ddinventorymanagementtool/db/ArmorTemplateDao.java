package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface ArmorTemplateDao {
    @Insert
    Long insertTemplate(ArmorTemplate armorTemplate);

    @Insert
    List<Long> insertAllTemplates(List<ArmorTemplate> armorTemplates);

    @Query("SELECT * FROM ArmorTemplate")
    LiveData<List<ArmorTemplate>> getAllTemplates();
}
