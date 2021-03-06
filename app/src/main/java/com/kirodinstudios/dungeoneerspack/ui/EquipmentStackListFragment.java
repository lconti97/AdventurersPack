package com.kirodinstudios.dungeoneerspack.ui;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirodinstudios.dungeoneerspack.R;
import com.kirodinstudios.dungeoneerspack.databinding.EquipmentStackListFragmentBinding;
import com.kirodinstudios.dungeoneerspack.viewmodel.EquipmentStackListViewModel;


public class EquipmentStackListFragment extends Fragment {

    public static final String TAG = "EquipmentStackListFragment";

    private final EquipmentStackClickCallback mEquipmentStackClickCallback = equipmentStack -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).showEquipmentStackDetailFragment(equipmentStack);
        }
    };
    private final View.OnClickListener mAddEquipmentStackOnClickListener = view -> {
        ((MainActivity) getActivity()).showEquipmentStackAddFragment();
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
        mBinding.setAddButtonOnClickListener(mAddEquipmentStackOnClickListener);

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
