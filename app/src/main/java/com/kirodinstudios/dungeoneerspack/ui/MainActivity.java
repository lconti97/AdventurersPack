package com.kirodinstudios.dungeoneerspack.ui;

import android.os.Bundle;

import com.kirodinstudios.dungeoneerspack.R;
import com.kirodinstudios.dungeoneerspack.model.EquipmentStack;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showEquipmentStackListFragment();
        }
    }

    public void showEquipmentStackListFragment() {
        EquipmentStackListFragment equipmentStackListFragment = new EquipmentStackListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, equipmentStackListFragment, null)
                .commit();
    }

    public void showEquipmentStackAddFragment() {
        EquipmentStackAddFragment equipmentStackAddFragment = new EquipmentStackAddFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("equipmentStack")
                .replace(R.id.fragment_container, equipmentStackAddFragment, null)
                .commit();
    }

    public void showEquipmentStackDetailFragment(EquipmentStack equipmentStack) {
        EquipmentStackDetailFragment equipmentStackDetailFragment = EquipmentStackDetailFragment.forEquipmentStack(equipmentStack.getEquipmentStackId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("equipmentStack")
                .replace(R.id.fragment_container, equipmentStackDetailFragment, null)
                .commit();
    }
}
