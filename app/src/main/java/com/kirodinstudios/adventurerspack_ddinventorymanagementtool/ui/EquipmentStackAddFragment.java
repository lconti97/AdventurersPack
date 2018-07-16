package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel.EquipmentStackAddViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class EquipmentStackAddFragment extends Fragment {
    private EquipmentStackAddViewModel equipmentStackViewModel;
    private EditText nameEditText;
    private EditText countEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_stack_add_fragment, container, false);
        equipmentStackViewModel = ViewModelProviders.of(this).get(EquipmentStackAddViewModel.class);

        nameEditText = view.findViewById(R.id.equipment_stack_add_fragment_name);
        countEditText = view.findViewById(R.id.equipment_stack_add_fragment_count);
        FloatingActionButton addButton = view.findViewById(R.id.equipment_stack_add_fragment_done);

        addButton.setOnClickListener(view1 -> {
            String name = nameEditText.getText().toString();
            int count = Integer.valueOf(countEditText.getText().toString());
            EquipmentStack equipmentStack = new EquipmentStack(name, count);

            equipmentStackViewModel.addEquipmentStack(equipmentStack);
            ((MainActivity) getActivity()).showEquipmentStackListFragment();
        });

        return view;
    }
}
