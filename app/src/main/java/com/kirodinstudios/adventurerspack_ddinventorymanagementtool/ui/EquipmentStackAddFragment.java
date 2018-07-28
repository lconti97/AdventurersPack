package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.EquipmentTemplateAdapter;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTypes;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel.EquipmentStackAddViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

public class EquipmentStackAddFragment extends Fragment {
    private EquipmentStackAddViewModel viewModel;
    private AutoCompleteTextView nameAutoCompleteTextView;
    private EditText countEditText;
    private Spinner typeSpinner;
    private EquipmentTemplate equipmentTemplate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_stack_add_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(EquipmentStackAddViewModel.class);

        nameAutoCompleteTextView = view.findViewById(R.id.equipment_stack_add_fragment_name);
        countEditText = view.findViewById(R.id.equipment_stack_add_fragment_count);
        FloatingActionButton addButton = view.findViewById(R.id.equipment_stack_add_fragment_done);
        typeSpinner = view.findViewById(R.id.equipment_stack_add_fragment_type);

        addButton.setOnClickListener(view1 -> {
            String name = nameAutoCompleteTextView.getText().toString();
            int count = Integer.valueOf(countEditText.getText().toString());
            long equipmentTemplateId = equipmentTemplate.getEquipmentTemplateId();
            EquipmentStack equipmentStack = new EquipmentStack(name, count, equipmentTemplateId);

            viewModel.addEquipmentStack(equipmentStack);
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
            ArrayAdapter<String> typeSpinnerAdapter = (ArrayAdapter<String>) typeSpinner.getAdapter();
            int position = typeSpinnerAdapter.getPosition(equipmentTemplate.getEquipmentType());
            typeSpinner.setSelection(position, true);
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                EquipmentTypes.EQUIPMENT_TYPES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdapter);

        return view;
    }
}
