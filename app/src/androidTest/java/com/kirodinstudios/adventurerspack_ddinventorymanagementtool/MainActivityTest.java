package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.db.AppDatabase;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.WeaponTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui.MainActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @BeforeClass
    public static void deleteDatabase() {
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void disableRecyclerViewAnimations() {
//        EspressoTestUtil.disableAnimations(mActivityRule);
    }

    @Test
    public void testCanCreateEquipmentStackAndTemplate() {
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
    public void testCanPopulateFieldsFromArmorTemplate() {
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
                .check(matches(allOf(isDisplayed(), withText("40"))));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .check(matches(allOf(isDisplayed(), withText("750"))));
        onView(withId(R.id.armor_class))
                .check(matches(allOf(isDisplayed(), withSubstring("15 + Dex"))));
        onView(withId(R.id.armor_category))
                .check(matches(allOf(isDisplayed(), withSpinnerText("Medium Armor"))));
        onView(withId(R.id.armor_disadvantage_on_stealth))
                .check(matches(allOf(isDisplayed(), isChecked())));
        onView(withId(R.id.armor_requires_minimum_strength))
                .check(matches(allOf(isDisplayed(), isNotChecked())));
        onView(withId(R.id.armor_minimum_strength))
                .check(matches(allOf(isDisplayed(), withText(""))));
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .check(matches(allOf(isDisplayed(), withSubstring("consists of shaped metal plates"))));

        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());
        onView(withSubstring("Half plate"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCanPopulateFieldsFromWeaponTemplate() {
        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_name))
                .perform(typeText("sw"));
        onView(withSubstring("Shortsword"))
                .inRoot(isPlatformPopup())
                .perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.equipment_stack_add_fragment_type))
                .check(matches(allOf(isDisplayed(), withSpinnerText("Weapon"))));
        onView(withId(R.id.equipment_stack_add_fragment_weight))
                .check(matches(allOf(isDisplayed(), withText("2"))));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .check(matches(allOf(isDisplayed(), withText("10"))));
        onView(withId(R.id.weapon_category))
                .check(matches(allOf(isDisplayed(), withSpinnerText("Martial melee weapon"))));
        onView(withId(R.id.weapon_damage))
                .check(matches(allOf(isDisplayed(), withText("1d6 piercing"))));
        onView(withId(R.id.weapon_properties))
                .check(matches(allOf(isDisplayed(), withText("Finesse, light"))));
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .check(matches(allOf(isDisplayed(), withText(""))));

        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());
        onView(withSubstring("Shortsword"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCanAddWeaponTemplateAndCreateANewStackWithIt() {
        WeaponTemplate weaponTemplate = new WeaponTemplate("name", "description", 6.8, 3.4,
                "1d10 piercing", "Heavy, finesse", false, true);
        int count = 7;

        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_name))
                .perform(typeText(weaponTemplate.getName()));
        onView(withId(R.id.equipment_stack_add_fragment_count))
                .perform(typeText(String.valueOf(count)));
        onView(withId(R.id.equipment_stack_add_fragment_type))
                .perform(click());
        onView(withText("Weapon"))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_weight))
                .perform(typeText(String.valueOf(weaponTemplate.getWeightInPounds())));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .perform(typeText(String.valueOf(weaponTemplate.getCostInGp())));
        onView(withId(R.id.weapon_damage))
                .perform(typeText(weaponTemplate.getDamage()));
        onView(withId(R.id.weapon_properties))
                .perform(typeText(weaponTemplate.getProperties()));
        closeSoftKeyboard();
        onView(withId(R.id.weapon_category))
                .perform(click());
        onView(withText("Martial melee weapon"))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .perform(typeText(weaponTemplate.getDescription()));

        onView(withId(R.id.equipment_stack_add_fragment_done))
                .perform(click());

        onView(withText(weaponTemplate.getName()))
                .check(matches(isDisplayed()));

        onView(withId(R.id.add_equipment_stack_button))
                .perform(click());
        onView(withId(R.id.equipment_stack_add_fragment_name))
                .perform(typeText(weaponTemplate.getName()));
        onView(withText(weaponTemplate.getName()))
                .inRoot(isPlatformPopup())
                .perform(click());

        onView(withId(R.id.equipment_stack_add_fragment_type))
                .check(matches(withSpinnerText("Weapon")));
        onView(withId(R.id.equipment_stack_add_fragment_count))
                .check(matches(withText("1")));
        onView(withId(R.id.equipment_stack_add_fragment_weight))
                .check(matches(withText(String.valueOf(weaponTemplate.getWeightInPounds()))));
        onView(withId(R.id.equipment_stack_add_fragment_cost))
                .check(matches(withText(String.valueOf(weaponTemplate.getCostInGp()))));
        onView(withId(R.id.weapon_damage))
                .check(matches(withText(weaponTemplate.getDamage())));
        onView(withId(R.id.weapon_properties))
                .check(matches(withText(weaponTemplate.getProperties())));
        onView(withId(R.id.weapon_category))
                .check(matches(withSpinnerText("Martial melee weapon")));
        onView(withId(R.id.equipment_stack_add_fragment_description))
                .check(matches(withText(weaponTemplate.getDescription())));
    }

    @Test
    public void testWeaponFieldsShowInAddFragment() {
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
