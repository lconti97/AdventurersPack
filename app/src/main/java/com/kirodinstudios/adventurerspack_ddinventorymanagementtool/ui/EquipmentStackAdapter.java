package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.databinding.EquipmentStackListItemBinding;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EquipmentStackAdapter extends RecyclerView.Adapter<EquipmentStackAdapter.EquipmentStackViewHolder> {

    @Nullable
    private final EquipmentStackClickCallback mEquipmentStackClickCallback;
    private List<EquipmentStack> mEquipmentStacks;

    public EquipmentStackAdapter(@Nullable EquipmentStackClickCallback equipmentStackClickCallback) {
        mEquipmentStackClickCallback = equipmentStackClickCallback;
    }

    public void setEquipmentStacks(final List<EquipmentStack> equipmentStacks) {
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
                    return oldEquipmentStack.getEquipmentTemplateId().equals(newEquipmentStack.getEquipmentTemplateId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    EquipmentStack oldEquipmentStack = mEquipmentStacks.get(oldItemPosition);
                    EquipmentStack newEquipmentStack = mEquipmentStacks.get(newItemPosition);
                    return oldEquipmentStack.getEquipmentTemplateId().equals(newEquipmentStack.getEquipmentTemplateId())
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
        EquipmentStackListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.equipment_stack_list_item,
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

        final EquipmentStackListItemBinding binding;

        EquipmentStackViewHolder(EquipmentStackListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
