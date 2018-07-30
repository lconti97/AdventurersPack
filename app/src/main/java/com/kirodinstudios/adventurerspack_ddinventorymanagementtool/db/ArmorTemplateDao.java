package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ArmorTemplateDao {
    @Insert
    List<Long> insertAllTemplates(Collection<ArmorTemplate> armorTemplate);

    @Query("SELECT * FROM ArmorTemplate")
    LiveData<List<ArmorTemplate>> getAllTemplates();
}
