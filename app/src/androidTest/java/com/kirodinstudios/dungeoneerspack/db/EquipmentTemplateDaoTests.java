package com.kirodinstudios.dungeoneerspack.db;

import com.kirodinstudios.dungeoneerspack.LiveDataTestUtil;
import com.kirodinstudios.dungeoneerspack.model.EquipmentTemplate;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertNotNull;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class EquipmentTemplateDaoTests {
    private AppDatabase database;
    private EquipmentTemplateDao equipmentTemplateDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        equipmentTemplateDao = database.equipmentTemplateDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getAllTemplates() throws InterruptedException {
        LiveData<List<EquipmentTemplate>> equipmentTemplates = equipmentTemplateDao.getAllEquipmentTemplates();

        assertNotNull(LiveDataTestUtil.getValue(equipmentTemplates));
    }
}
