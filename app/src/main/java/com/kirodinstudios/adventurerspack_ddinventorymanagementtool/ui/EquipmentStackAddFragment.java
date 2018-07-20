package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class EquipmentStackAddFragment extends Fragment {
    private EquipmentStackAddViewModel equipmentStackViewModel;
    private AutoCompleteTextView nameAutoCompleteTextView;
    private EditText countEditText;
    private Spinner typeSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_stack_add_fragment, container, false);
        equipmentStackViewModel = ViewModelProviders.of(this).get(EquipmentStackAddViewModel.class);

        nameAutoCompleteTextView = view.findViewById(R.id.equipment_stack_add_fragment_name);
        countEditText = view.findViewById(R.id.equipment_stack_add_fragment_count);
        FloatingActionButton addButton = view.findViewById(R.id.equipment_stack_add_fragment_done);
        typeSpinner = view.findViewById(R.id.equipment_stack_add_fragment_type);

        addButton.setOnClickListener(view1 -> {
            String name = nameAutoCompleteTextView.getText().toString();
            int count = Integer.valueOf(countEditText.getText().toString());
            EquipmentStack equipmentStack = new EquipmentStack(name, count);

            equipmentStackViewModel.addEquipmentStack(equipmentStack);
            ((MainActivity) getActivity()).showEquipmentStackListFragment();
        });

        List<EquipmentTemplate> equipmentTemplates = Arrays.asList();
//                new EquipmentTemplate("Sword", EquipmentTypes.WEAPON),
//                new EquipmentTemplate("Shield", EquipmentTypes.SHIELD));
        EquipmentTemplateAdapter nameAutoCompleteTextViewAdapter = new EquipmentTemplateAdapter(
                getContext(),
                equipmentTemplates);
        nameAutoCompleteTextView.setAdapter(nameAutoCompleteTextViewAdapter);
        nameAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EquipmentTemplate equipmentTemplate = (EquipmentTemplate) nameAutoCompleteTextView.getAdapter().getItem(i);
                ArrayAdapter<String> typeSpinnerAdapter = (ArrayAdapter<String>) typeSpinner.getAdapter();
                int position = typeSpinnerAdapter.getPosition(equipmentTemplate.getEquipmentType());
                typeSpinner.setSelection(position, true);
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                EquipmentTypes.EQUIPMENT_TYPES);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdapter);

        return view;
    }
}
