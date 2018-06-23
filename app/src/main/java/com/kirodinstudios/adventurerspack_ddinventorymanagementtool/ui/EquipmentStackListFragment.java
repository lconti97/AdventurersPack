package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.databinding.EquipmentStackListFragmentBinding;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.viewmodel.EquipmentStackListViewModel;


public class EquipmentStackListFragment extends Fragment {

    public static final String TAG = "EquipmentStackListFragment";
    private final EquipmentStackClickCallback mEquipmentStackClickCallback = equipmentStack -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(equipmentStack);
        }
    };
    private EquipmentStackAdapter mEquipmentStackAdapter;
    private EquipmentStackListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.equipment_stack_list_fragment, container, false);

        mEquipmentStackAdapter = new EquipmentStackAdapter(mEquipmentStackClickCallback);
        mBinding.equipmentStacksList.setAdapter(mEquipmentStackAdapter);
        mBinding.equipmentStacksList.addItemDecoration(new DividerItemDecoration(
                mBinding.equipmentStacksList.getContext(), OrientationHelper.VERTICAL));

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EquipmentStackListViewModel viewModel =
                ViewModelProviders.of(this).get(EquipmentStackListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(EquipmentStackListViewModel viewModel) {
        viewModel.getEquipmentStacks().observe(this, equipmentStackEntities -> {
            if (equipmentStackEntities != null) {
                mBinding.setIsLoading(false);
                mEquipmentStackAdapter.setEquipmentStacks(equipmentStackEntities);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }
}
