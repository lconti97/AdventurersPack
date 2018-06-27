package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.runner.AndroidJUnit4;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackDao;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class EquipmentStackDaoTest {
    private AppDatabase database;
    private EquipmentStackDao equipmentStackDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        equipmentStackDao = database.equipmentStackDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getEquipmentStacksWhenNoEquipmentStacksInserted() throws InterruptedException {
        List<EquipmentStackEntity> equipmentStackEntities = LiveDataTestUtil.getValue(equipmentStackDao.loadAll());

        assertTrue(equipmentStackEntities.isEmpty());
    }

    @Test
    public void getEquipmentStacksAfterInserted() throws InterruptedException {
        equipmentStackDao.insertAll(TestData.EQUIPMENT_STACKS);

        List<EquipmentStackEntity> equipmentStackEntities = LiveDataTestUtil.getValue(equipmentStackDao.loadAll());

        assertThat(equipmentStackEntities.size(), Matchers.is(2));
    }
}
