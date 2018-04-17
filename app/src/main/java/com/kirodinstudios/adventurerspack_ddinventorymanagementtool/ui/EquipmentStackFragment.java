package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.databinding.EquipmentStackFragmentBinding;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel.EquipmentStackViewModel;

public class EquipmentStackFragment extends Fragment {

    private static final String KEY_EQUIPMENT_STACK_ID = "equipment_stack_id";

    private EquipmentStackFragmentBinding mBinding;

    public static EquipmentStackFragment forEquipmentStack(int equipmentStackId) {
        EquipmentStackFragment fragment = new EquipmentStackFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_EQUIPMENT_STACK_ID, equipmentStackId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.equipment_stack_fragment, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EquipmentStackViewModel.Factory factory = new EquipmentStackViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_EQUIPMENT_STACK_ID));

        final EquipmentStackViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(EquipmentStackViewModel.class);

        mBinding.setEquipmentStackViewModel(viewModel);

        subscribeToModel(viewModel);
    }

    private void subscribeToModel(final EquipmentStackViewModel viewModel) {
        viewModel.getObservableEquipmentStack().observe(this, viewModel::setEquipmentStack);
    }
}
