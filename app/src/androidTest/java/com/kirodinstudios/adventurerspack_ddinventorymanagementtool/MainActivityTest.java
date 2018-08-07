package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

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

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.CountingTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
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
    public void canCreateEquipmentStackAndTemplate() {
        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_name))
                .perform(typeText("xq"));
        onView(withId(R.id.equipment_stack_add_fragment_count))
                .perform(typeText("5"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());

        onView(withId(R.id.equipment_stacks_list))
                .check(matches(isDisplayed()));
        onView(withText("xq"))
                .perform(click());

        onView(withId(R.id.equipment_stack_detail_item))
                .check(matches(isDisplayed()));
    }

    @Test
    public void canPopulateFieldsFromTemplate() {
        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_name))
                .perform(typeText("pl"));
        onView(withSubstring("Half plate"))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.equipment_stack_add_fragment_type))
                .check(matches(withSpinnerText("Armor")));
        onView(withId(R.id.equipment_stack_add_fragment_weight))
                .check(matches(withText("750")));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .check(matches(withText("750")));
        onView(withId(R.id.equipment_stack_add_fragment_armor_class))
                .check(matches(withSubstring("15 + Dex")));
        onView(withId(R.id.equipment_stack_add_fragment_armor_category))
                .check(matches(withSpinnerText("Medium Armor")));
        onView(withId(R.id.equipment_stack_add_fragment_disadvantage_on_stealth))
                .check(matches(isChecked()));
        onView(withId(R.id.equipment_stack_add_fragment_requires_minimum_strength))
                .check(matches(isNotChecked()));
        onView(withId(R.id.equipment_stack_add_fragment_minimum_strength))
                .check(matches(withText("")));
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .check(matches(withSubstring("consists of shaped metal plates")));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());
        onView(withSubstring("Half plate"))
                .check(matches(isDisplayed()));
    }

    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}
