package com.kirodinstudios.dungeoneerspack.ui;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirodinstudios.dungeoneerspack.R;
import com.kirodinstudios.dungeoneerspack.databinding.EquipmentStackDetailFragmentBinding;
import com.kirodinstudios.dungeoneerspack.viewmodel.EquipmentStackViewModel;

public class EquipmentStackDetailFragment extends Fragment {

    private static final String KEY_EQUIPMENT_STACK_ID = "equipment_stack_id";

    private EquipmentStackDetailFragmentBinding mBinding;

    public static EquipmentStackDetailFragment forEquipmentStack(int equipmentStackId) {
        EquipmentStackDetailFragment fragment = new EquipmentStackDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_EQUIPMENT_STACK_ID, equipmentStackId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.equipment_stack_detail_fragment, container, false);

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
