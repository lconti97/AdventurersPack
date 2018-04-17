package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.databinding.EquipmentStackItemBinding;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.List;

public class EquipmentStackAdapter extends RecyclerView.Adapter<EquipmentStackAdapter.EquipmentStackViewHolder> {

    @Nullable
    private final EquipmentStackClickCallback mEquipmentStackClickCallback;
    private List<? extends EquipmentStack> mEquipmentStacks;

    public EquipmentStackAdapter(@Nullable EquipmentStackClickCallback equipmentStackClickCallback) {
        mEquipmentStackClickCallback = equipmentStackClickCallback;
    }

    public void setEquipmentStacks(final List<? extends EquipmentStack> equipmentStacks) {
        if (mEquipmentStacks == null) {
            mEquipmentStacks = equipmentStacks;
            notifyItemRangeInserted(0, equipmentStacks.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mEquipmentStacks.size();
                }

                @Override
                public int getNewListSize() {
                    return equipmentStacks.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    EquipmentStack oldEquipmentStack = mEquipmentStacks.get(oldItemPosition);
                    EquipmentStack newEquipmentStack = equipmentStacks.get(newItemPosition);
                    return oldEquipmentStack.getId() == newEquipmentStack.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    EquipmentStack oldEquipmentStack = mEquipmentStacks.get(oldItemPosition);
                    EquipmentStack newEquipmentStack = mEquipmentStacks.get(newItemPosition);
                    return oldEquipmentStack.getId() == newEquipmentStack.getId()
                            && oldEquipmentStack.getName().equals(newEquipmentStack.getName())
                            && oldEquipmentStack.getCount() == newEquipmentStack.getCount();
                }
            });
            mEquipmentStacks = equipmentStacks;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public EquipmentStackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EquipmentStackItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.equipment_stack_item,
                        parent, false);
        binding.setCallback(mEquipmentStackClickCallback);

        return new EquipmentStackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EquipmentStackViewHolder holder, int position) {
        holder.binding.setEquipmentStack(mEquipmentStacks.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mEquipmentStacks == null ? 0 : mEquipmentStacks.size();
    }

    static class EquipmentStackViewHolder extends RecyclerView.ViewHolder {

        final EquipmentStackItemBinding binding;

        EquipmentStackViewHolder(EquipmentStackItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
