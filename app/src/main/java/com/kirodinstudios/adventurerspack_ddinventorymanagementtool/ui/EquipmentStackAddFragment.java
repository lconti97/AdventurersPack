package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.EquipmentTemplateAdapter;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorCategories;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.ArmorTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTypes;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel.EquipmentStackAddViewModel;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class EquipmentStackAddFragment extends Fragment {
    private View[] armorViewsToHide;

    private EquipmentStackAddViewModel viewModel;
    private ConstraintLayout constraintLayout;
    private AutoCompleteTextView nameAutoCompleteTextView;
    private EditText countEditText;
    private Spinner equipmentTypeSpinner;
    private EditText weightEditText;
    private EditText costEditText;
    private EditText descriptionEditText;
    private TextInputLayout armorClassTextInputLayout;
    private EditText armorClassEditText;
    private Spinner armorCategorySpinner;
    private CheckBox armorGivesDisadvantageOnStealthCheckBox;
    private CheckBox armorHasMinimumStrengthRequirementCheckBox;
    private TextInputLayout armorMinimumStrengthLayout;
    private EditText armorMinimumStrengthEditText;
    private EquipmentTemplate equipmentTemplate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_stack_add_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(EquipmentStackAddViewModel.class);

        constraintLayout = view.findViewById(R.id.equipment_stack_add_fragment_layout);
        nameAutoCompleteTextView = view.findViewById(R.id.equipment_stack_add_fragment_name);
        countEditText = view.findViewById(R.id.equipment_stack_add_fragment_count);
        FloatingActionButton addButton = view.findViewById(R.id.equipment_stack_add_fragment_done);
        equipmentTypeSpinner = view.findViewById(R.id.equipment_stack_add_fragment_type);
        weightEditText = view.findViewById(R.id.equipment_stack_add_fragment_weight);
        costEditText = view.findViewById(R.id.equipment_stack_add_fragment_cost);
        setupArmorViews(view);
        descriptionEditText = view.findViewById(R.id.equipment_stack_add_fragment_description);

        addButton.setOnClickListener(view1 -> {
            String name = nameAutoCompleteTextView.getText().toString();
            int count = Integer.valueOf(countEditText.getText().toString());

            //TODO: if equipment template is null or inputs don't match up with the equipment stack properties
            if (equipmentTemplate == null) {
                EquipmentTemplate equipmentTemplate;

                String description = descriptionEditText.getText().toString();
                String weightText = weightEditText.getText().toString();
                Double weight = weightText.isEmpty() ? null : Double.valueOf(weightText);
                String costText = costEditText.getText().toString();
                Double cost = costText.isEmpty() ? null : Double.valueOf(costText);
                String equipmentType = (String) equipmentTypeSpinner.getSelectedItem();

                if (equipmentType.equals(EquipmentTypes.ARMOR)) {
                    String armorClass = armorClassEditText.getText().toString();
                    String armorCategory = (String) armorCategorySpinner.getSelectedItem();
                    Boolean givesDisadvantageOnStealthChecks = armorGivesDisadvantageOnStealthCheckBox.isSelected();
                    Boolean hasMinimumStrength = armorHasMinimumStrengthRequirementCheckBox.isSelected();
                    String minimumStrengthText = armorMinimumStrengthEditText.getText().toString();
                    Integer minimumStrength = minimumStrengthText.isEmpty() ? null : Integer.valueOf(minimumStrengthText);

                    equipmentTemplate = new ArmorTemplate(name, description, cost, weight,
                            armorClass, armorCategory, givesDisadvantageOnStealthChecks,
                            hasMinimumStrength, minimumStrength);
                }
                else {
                    equipmentTemplate = new EquipmentTemplate(name, description, equipmentType, cost, weight);
                }

                EquipmentStack equipmentStack = new EquipmentStack(name, count);
                viewModel.addEquipmentTemplateAndEquipmentStack(equipmentTemplate, equipmentStack);
            }
            else {
                long equipmentTemplateId = equipmentTemplate.getEquipmentTemplateId();
                EquipmentStack equipmentStack = new EquipmentStack(name, count, equipmentTemplateId);

                viewModel.addEquipmentStack(equipmentStack);
            }

            ((MainActivity) getActivity()).showEquipmentStackListFragment();
        });

        LiveData<List<EquipmentTemplate>> equipmentTemplates = viewModel.getAllEquipmentTemplates();
        equipmentTemplates.observe(this, equipmentTemplates1 -> {});
        EquipmentTemplateAdapter nameAutoCompleteTextViewAdapter = new EquipmentTemplateAdapter(
                getContext(),
                equipmentTemplates);
        nameAutoCompleteTextView.setAdapter(nameAutoCompleteTextViewAdapter);
        nameAutoCompleteTextView.setOnItemClickListener((adapterView, view12, i, l) -> {
            equipmentTemplate = (EquipmentTemplate) nameAutoCompleteTextView.getAdapter().getItem(i);
            ArrayAdapter<String> typeSpinnerAdapter = (ArrayAdapter<String>) equipmentTypeSpinner.getAdapter();
            int position = typeSpinnerAdapter.getPosition(equipmentTemplate.getEquipmentType());
            equipmentTypeSpinner.setSelection(position, true);
            costEditText.setText(getStringRepresentationOfDouble(equipmentTemplate.getCostInGp()));
            weightEditText.setText(getStringRepresentationOfDouble(equipmentTemplate.getCostInGp()));
            descriptionEditText.setText(equipmentTemplate.getDescription());
        });

        Arrays.sort(EquipmentTypes.EQUIPMENT_TYPES);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                EquipmentTypes.EQUIPMENT_TYPES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentTypeSpinner.setAdapter(spinnerAdapter);
        equipmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getSelectedItem();
                if (selection.equals(EquipmentTypes.ARMOR)) {
                    setVisibleWithoutChangingFocus(constraintLayout, armorViewsToHide);
                }
                else {
                    for (View target : armorViewsToHide)
                        target.setVisibility(View.GONE);
                }
                descriptionEditText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }

        });

        return view;
    }

    private void setupArmorViews(View view) {
        armorClassTextInputLayout = view.findViewById(R.id.equipment_stack_add_fragment_armor_class_layout);
        armorClassEditText = view.findViewById(R.id.equipment_stack_add_fragment_armor_class);
        armorCategorySpinner = view.findViewById(R.id.equipment_stack_add_fragment_armor_category);
        armorGivesDisadvantageOnStealthCheckBox = view.findViewById(R.id.equipment_stack_add_fragment_disadvantage_on_stealth);
        armorHasMinimumStrengthRequirementCheckBox = view.findViewById(R.id.equipment_stack_add_fragment_requires_minimum_strength);
        armorMinimumStrengthLayout = view.findViewById(R.id.equipment_stack_add_fragment_minimum_strength_layout);
        armorMinimumStrengthEditText = view.findViewById(R.id.equipment_stack_add_fragment_minimum_strength);

        armorViewsToHide = new View[] {
                armorClassTextInputLayout,
                armorCategorySpinner,
                armorGivesDisadvantageOnStealthCheckBox,
                armorGivesDisadvantageOnStealthCheckBox,
                armorHasMinimumStrengthRequirementCheckBox,
                armorMinimumStrengthLayout
        };

        ArrayAdapter<String> armorCategoryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                ArmorCategories.ARMOR_CATEGORIES);
        armorCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        armorCategorySpinner.setAdapter(armorCategoryAdapter);


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
