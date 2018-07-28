package com.kirodinstudios.adventurerspack_ddinventorymanagementtool;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;


public class EquipmentTemplateAdapter extends BaseAdapter implements Filterable {
    private static final int MINIMUM_FILTER_STRING_LENGTH = 2;

    private Filter filter;
    private Context context;
    private List<EquipmentTemplate> filteredEquipmentTemplates;

    public EquipmentTemplateAdapter(Context context, final LiveData<List<EquipmentTemplate>> equipmentTemplates) {
        this.context = context;
        this.filter = new EquipmentTemplateFilter(equipmentTemplates);
        this.filteredEquipmentTemplates = Collections.emptyList();
    }

    @Override
    public int getCount() {
        return filteredEquipmentTemplates.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredEquipmentTemplates.get(i);
    }

    @Override
    public long getItemId(int i) {
        return filteredEquipmentTemplates.get(i).getEquipmentTemplateId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        EquipmentTemplate equipmentTemplate = filteredEquipmentTemplates.get(i);

        View listItem = LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);

        TextView textView = listItem.findViewById(android.R.id.text1);
        textView.setText(equipmentTemplate.getName());

        return listItem;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class EquipmentTemplateFilter extends Filter {
        private LiveData<List<EquipmentTemplate>> equipmentTemplates;

        EquipmentTemplateFilter(LiveData<List<EquipmentTemplate>> equipmentTemplates) {
            this.equipmentTemplates = equipmentTemplates;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<EquipmentTemplate> results = new ArrayList<>();
            if (constraint != null && constraint.length() >= MINIMUM_FILTER_STRING_LENGTH && equipmentTemplates.getValue() != null) {
                String filterString = constraint.toString().toLowerCase();

                for (EquipmentTemplate equipmentTemplate : equipmentTemplates.getValue()) {
                    if (equipmentTemplate.getName().toLowerCase().contains(filterString))
                        results.add(equipmentTemplate);
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = results;
            filterResults.count = results.size();

            return filterResults;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            try {
                filteredEquipmentTemplates = (List<EquipmentTemplate>) filterResults.values;
                notifyDataSetChanged();
            }
            catch (ClassCastException e) {
                Log.e(Constants.LOG_TAG, Log.getStackTraceString(e));
            }
        }

        @Override
        public String convertResultToString(Object object) {
            EquipmentTemplate equipmentTemplate = (EquipmentTemplate) object;
            return equipmentTemplate.getName();
        }
    }
}
