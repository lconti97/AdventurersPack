package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackDao;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.EquipmentStackEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.kirodinstudios.adventurerspack_ddinventorymanagementtool.TestData.EQUIPMENT_STACKS;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EquipmentStackDaoTest {
    private AppDatabase database;
    private EquipmentStackDao equipmentStackDao;

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
        equipmentStackDao.insertAll(EQUIPMENT_STACKS);

        List<EquipmentStackEntity> equipmentStackEntities = LiveDataTestUtil.getValue(equipmentStackDao.loadAll());

        assertThat(equipmentStackEntities.size(), is(2));
    }
}
