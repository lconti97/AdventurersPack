package com.kirodinstudios.dungeoneerspack.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kirodinstudios.dungeoneerspack.EquipmentTemplateAdapter;
import com.kirodinstudios.dungeoneerspack.R;
import com.kirodinstudios.dungeoneerspack.model.ArmorCategories;
import com.kirodinstudios.dungeoneerspack.model.ArmorTemplate;
import com.kirodinstudios.dungeoneerspack.model.EquipmentStack;
import com.kirodinstudios.dungeoneerspack.model.EquipmentTemplate;
import com.kirodinstudios.dungeoneerspack.model.EquipmentTypes;
import com.kirodinstudios.dungeoneerspack.model.WeaponTemplate;
import com.kirodinstudios.dungeoneerspack.model.WeaponTypes;
import com.kirodinstudios.dungeoneerspack.viewmodel.EquipmentStackAddViewModel;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class EquipmentStackAddFragment extends Fragment {
    private EquipmentStackAddViewModel viewModel;
    private EquipmentTemplate equipmentTemplate;

    private ConstraintLayout constraintLayout;
    private AutoCompleteTextView nameAutoCompleteTextView;
    private EditText countEditText;
    private Spinner equipmentTypeSpinner;
    private EditText weightEditText;
    private EditText costEditText;
    private EditText descriptionEditText;
    private FloatingActionButton addButton;

    private TextInputLayout armorClassTextInputLayout;
    private EditText armorClassEditText;
    private Spinner armorCategorySpinner;
    private CheckBox armorGivesDisadvantageOnStealthCheckBox;
    private CheckBox armorHasMinimumStrengthRequirementCheckBox;
    private TextInputLayout armorMinimumStrengthLayout;
    private EditText armorMinimumStrengthEditText;

    private Spinner weaponTypeSpinner;
    private EditText weaponDamageEditText;
    private EditText weaponPropertiesEditText;

    private ViewFlipper additionalFieldsViewFlipper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_stack_add_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(EquipmentStackAddViewModel.class);

        setupBaseViews(view);
        setupArmorViews(view);
        setupWeaponViews(view);

        return view;
    }

    private void setupBaseViews(View view) {
        constraintLayout = view.findViewById(R.id.equipment_stack_add_fragment_layout);
        nameAutoCompleteTextView = view.findViewById(R.id.equipment_stack_add_fragment_name);
        countEditText = view.findViewById(R.id.equipment_stack_add_fragment_count);
        addButton = view.findViewById(R.id.equipment_stack_add_fragment_done);
        equipmentTypeSpinner = view.findViewById(R.id.equipment_stack_add_fragment_type);
        weightEditText = view.findViewById(R.id.equipment_stack_add_fragment_weight);
        costEditText = view.findViewById(R.id.equipment_stack_add_fragment_cost);
        additionalFieldsViewFlipper = view.findViewById(R.id.equipment_stack_add_fragment_additional_fields);
        descriptionEditText = view.findViewById(R.id.equipment_stack_add_fragment_description);

        addButton.setOnClickListener(view1 -> addEquipmentStack());

        nameAutoCompleteTextView.setAdapter(getNameAutocompleteTextViewAdapter());
        nameAutoCompleteTextView.setOnItemClickListener((adapterView, view12, i, l) -> {
            equipmentTemplate = (EquipmentTemplate) nameAutoCompleteTextView.getAdapter().getItem(i);
            populateInputFieldsFromEquipmentTemplate(equipmentTemplate);
        });

        equipmentTypeSpinner.setAdapter(getEquipmentTypeSpinnerAdapter());
        equipmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String equipmentType = (String) adapterView.getSelectedItem();
                showInputFieldsForEquipmentType(equipmentType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private EquipmentTemplateAdapter getNameAutocompleteTextViewAdapter() {
        LiveData<List<EquipmentTemplate>> equipmentTemplates = viewModel.getAllEquipmentTemplates();
        equipmentTemplates.observe(this, equipmentTemplates1 -> {});
        return new EquipmentTemplateAdapter(
                getContext(),
                equipmentTemplates);
    }

    private ArrayAdapter<String> getEquipmentTypeSpinnerAdapter() {
        Arrays.sort(EquipmentTypes.EQUIPMENT_TYPES);
        ArrayAdapter<String> equipmentTypeSpinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                EquipmentTypes.EQUIPMENT_TYPES);
        equipmentTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return equipmentTypeSpinnerAdapter;
    }

    private void showInputFieldsForEquipmentType(String equipmentType) {
        switch (equipmentType) {
            case EquipmentTypes.ARMOR:
                additionalFieldsViewFlipper.setDisplayedChild(0);
                setVisibleWithoutChangingFocus(constraintLayout, additionalFieldsViewFlipper);
                break;
            case EquipmentTypes.WEAPON:
                additionalFieldsViewFlipper.setDisplayedChild(1);
                setVisibleWithoutChangingFocus(constraintLayout, additionalFieldsViewFlipper);
                break;
            default:
                additionalFieldsViewFlipper.setVisibility(View.GONE);
                break;
        }
    }

    private void setupArmorViews(View view) {
        armorClassTextInputLayout = view.findViewById(R.id.armor_class_layout);
        armorClassEditText = view.findViewById(R.id.armor_class);
        armorCategorySpinner = view.findViewById(R.id.armor_category);
        armorGivesDisadvantageOnStealthCheckBox = view.findViewById(R.id.armor_disadvantage_on_stealth);
        armorHasMinimumStrengthRequirementCheckBox = view.findViewById(R.id.armor_requires_minimum_strength);
        armorMinimumStrengthLayout = view.findViewById(R.id.armor_minimum_strength_layout);
        armorMinimumStrengthEditText = view.findViewById(R.id.armor_minimum_strength);

        ArrayAdapter<String> armorCategoryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                ArmorCategories.ARMOR_CATEGORIES);
        armorCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        armorCategorySpinner.setAdapter(armorCategoryAdapter);
    }

    private void setupWeaponViews(View view) {
        weaponDamageEditText = view.findViewById(R.id.weapon_damage);
        weaponPropertiesEditText = view.findViewById(R.id.weapon_properties);
        weaponTypeSpinner = view.findViewById(R.id.weapon_category);

        ArrayAdapter<String> weaponTypeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                WeaponTypes.WEAPON_TYPES);
        weaponTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weaponTypeSpinner.setAdapter(weaponTypeAdapter);
    }

    private void addEquipmentStack() {
        int count = Integer.valueOf(countEditText.getText().toString());
        EquipmentStack equipmentStack = new EquipmentStack(count);

        //TODO: if equipment template is null or inputs don't match up with the equipment stack properties
        if (equipmentTemplate == null) {
            addEquipmentTemplateAndEquipmentStack(equipmentStack);
        }
        else {
            long equipmentTemplateId = equipmentTemplate.getEquipmentTemplateId();
            equipmentStack.setEquipmentTemplateId(equipmentTemplateId);
            equipmentStack.setName(equipmentTemplate.getName());

            viewModel.addEquipmentStack(equipmentStack);
        }

        ((MainActivity) getActivity()).showEquipmentStackListFragment();
    }

    private void addEquipmentTemplateAndEquipmentStack(EquipmentStack equipmentStack) {
        EquipmentTemplate equipmentTemplate;

        String name = nameAutoCompleteTextView.getText().toString();
        String description = descriptionEditText.getText().toString();
        String weightText = weightEditText.getText().toString();
        Double weight = weightText.isEmpty() ? null : Double.valueOf(weightText);
        String costText = costEditText.getText().toString();
        Double cost = costText.isEmpty() ? null : Double.valueOf(costText);
        String equipmentType = (String) equipmentTypeSpinner.getSelectedItem();

        switch (equipmentType) {
            case EquipmentTypes.ARMOR:
                String armorClass = armorClassEditText.getText().toString();
                String armorCategory = (String) armorCategorySpinner.getSelectedItem();
                Boolean givesDisadvantageOnStealthChecks = armorGivesDisadvantageOnStealthCheckBox.isSelected();
                Boolean hasMinimumStrength = armorHasMinimumStrengthRequirementCheckBox.isSelected();
                String minimumStrengthText = armorMinimumStrengthEditText.getText().toString();
                Integer minimumStrength = minimumStrengthText.isEmpty() ? null : Integer.valueOf(minimumStrengthText);

                equipmentTemplate = new ArmorTemplate(name, description, cost, weight,
                        armorClass, armorCategory, givesDisadvantageOnStealthChecks,
                        hasMinimumStrength, minimumStrength);
                break;
            case EquipmentTypes.WEAPON:
                String damage = weaponDamageEditText.getText().toString();
                String properties = weaponPropertiesEditText.getText().toString();
                boolean isSimpleWeapon = weaponTypeSpinner.getSelectedItem().toString().toLowerCase().contains("simple");
                boolean isMeleeWeapon = weaponTypeSpinner.getSelectedItem().toString().toLowerCase().contains("martial");

                equipmentTemplate = new WeaponTemplate(name, description, cost, weight,
                        damage, properties, isSimpleWeapon, isMeleeWeapon);
                break;
            default:
                equipmentTemplate = new EquipmentTemplate(name, description, cost, weight);
                break;
        }

        equipmentStack.setName(equipmentTemplate.getName());
        viewModel.addEquipmentTemplateAndEquipmentStack(equipmentTemplate, equipmentStack);
    }

    private void populateInputFieldsFromEquipmentTemplate(EquipmentTemplate equipmentTemplate) {
        Class<? extends EquipmentTemplate> equipmentTemplateClass = equipmentTemplate.getClass();

        ArrayAdapter<String> typeSpinnerAdapter = (ArrayAdapter<String>) equipmentTypeSpinner.getAdapter();
        String type = EquipmentTypes.getTypeStringFromClass(equipmentTemplateClass);
        int position = typeSpinnerAdapter.getPosition(type);
        equipmentTypeSpinner.setSelection(position, true);

        if (equipmentTemplate.getCostInGp() != null)
            costEditText.setText(getStringRepresentationOfDouble(equipmentTemplate.getCostInGp()));
        if (equipmentTemplate.getWeightInPounds() != null)
            weightEditText.setText(getStringRepresentationOfDouble(equipmentTemplate.getWeightInPounds()));
        if (equipmentTemplate.getDescription() != null)
            descriptionEditText.setText(equipmentTemplate.getDescription());

        if (equipmentTemplateClass.equals(ArmorTemplate.class)) {
            ArmorTemplate armorTemplate = (ArmorTemplate) equipmentTemplate;

            if (armorTemplate.getArmorClass() != null)
                armorClassEditText.setText(armorTemplate.getArmorClass());
            if (armorTemplate.getArmorCategory() != null) {
                ArrayAdapter<String> armorCategoryAdapter = (ArrayAdapter<String>) armorCategorySpinner.getAdapter();
                int armorCategoryPosition = armorCategoryAdapter.getPosition(armorTemplate.getArmorCategory());
                armorCategorySpinner.setSelection(armorCategoryPosition, true);
            }
            if (armorTemplate.getGivesDisadvantageOnStealthChecks() != null)
                armorGivesDisadvantageOnStealthCheckBox.setChecked(armorTemplate.getGivesDisadvantageOnStealthChecks());
            if (armorTemplate.getMinimumStrength() != null)
                armorHasMinimumStrengthRequirementCheckBox.setChecked(armorTemplate.getRequiresMinimumStrength());

            Integer minimumStrength = armorTemplate.getMinimumStrength();
            String minimumArmorStrengthText = minimumStrength == null ? "" : minimumStrength.toString();
            armorMinimumStrengthEditText.setText(minimumArmorStrengthText);
        }
        if (equipmentTemplateClass.equals(WeaponTemplate.class)) {
            WeaponTemplate weaponTemplate = (WeaponTemplate) equipmentTemplate;

            if (weaponTemplate.getIsMeleeWeapon() != null && weaponTemplate.getIsSimpleWeapon() != null) {
                ArrayAdapter<String> weaponTypeAdapter = (ArrayAdapter<String>) weaponTypeSpinner.getAdapter();
                int weaponTypePosition = weaponTypeAdapter.getPosition(weaponTemplate.getWeaponType());
                weaponTypeSpinner.setSelection(weaponTypePosition, true);
            }
            if (weaponTemplate.getDamage() != null)
                weaponDamageEditText.setText(weaponTemplate.getDamage());
            if (weaponTemplate.getProperties() != null)
                weaponPropertiesEditText.setText(weaponTemplate.getProperties());
        }
    }

    private void setVisibleWithoutChangingFocus(ViewGroup parent, View... targets) {
        int initialFocusability = parent.getDescendantFocusability();
        parent.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        for (View target : targets)
            target.setVisibility(View.VISIBLE);

        parent.setDescendantFocusability(initialFocusability);
    }

    @SuppressLint("DefaultLocale")
    private static String getStringRepresentationOfDouble(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
