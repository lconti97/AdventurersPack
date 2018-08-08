package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void disableRecyclerViewAnimations() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);
        EspressoTestUtil.disableAnimations(mActivityRule);
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
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.equipment_stack_add_fragment_type))
                .check(matches(allOf(isDisplayed(), withSpinnerText("Armor"))));
        onView(withId(R.id.equipment_stack_add_fragment_weight))
                .check(matches(allOf(isDisplayed(), withText("750"))));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .check(matches(allOf(isDisplayed(), withText("750"))));
        onView(withId(R.id.equipment_stack_add_fragment_armor_class))
                .check(matches(allOf(isDisplayed(), withSubstring("15 + Dex"))));
        onView(withId(R.id.equipment_stack_add_fragment_armor_category))
                .check(matches(allOf(isDisplayed(), withSpinnerText("Medium Armor"))));
        onView(withId(R.id.equipment_stack_add_fragment_disadvantage_on_stealth))
                .check(matches(allOf(isDisplayed(), isChecked())));
        onView(withId(R.id.equipment_stack_add_fragment_requires_minimum_strength))
                .check(matches(allOf(isDisplayed(), isNotChecked())));
        onView(withId(R.id.equipment_stack_add_fragment_minimum_strength))
                .check(matches(allOf(isDisplayed(), withText(""))));
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .check(matches(allOf(isDisplayed(), withSubstring("consists of shaped metal plates"))));

        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());
        onView(withSubstring("Half plate"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void weaponFieldsShowInAddFragment() {
        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_type))
                .perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Weapon")))
                .perform(click());

        onView(withId(R.id.weapon_damage))
                .check(matches(isDisplayed()));
        onView(withId(R.id.weapon_properties))
                .check(matches(isDisplayed()));
        onView(withId(R.id.weapon_category))
                .check(matches(isDisplayed()));
    }
}
