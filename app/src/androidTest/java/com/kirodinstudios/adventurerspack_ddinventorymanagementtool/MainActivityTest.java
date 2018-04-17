package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui.MainActivity;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();

    public MainActivityTest() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);
    }

    @Before
    public void disableRecyclerViewAnimations() {
        EspressoTestUtil.disableAnimations(mActivityRule);
    }

    @Before
    public void waitForDbCreation() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final LiveData<Boolean> databaseCreated = AppDatabase.getInstance(
                InstrumentationRegistry.getTargetContext(), new AppExecutors())
                .getDatabaseCreated();
        mActivityRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                databaseCreated.observeForever(new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (Boolean.TRUE.equals(aBoolean)) {
                            databaseCreated.removeObserver(this);
                            latch.countDown();
                        }
                    }
                });
            }
        });
        MatcherAssert.assertThat("database should've initialized",
                latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));
    }

    @Test
    public void clickOnFirstItem_opensDetailView() throws Throwable {
        drain();
        onView(ViewMatchers.withContentDescription(R.string.cd_equipment_stacks_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}
