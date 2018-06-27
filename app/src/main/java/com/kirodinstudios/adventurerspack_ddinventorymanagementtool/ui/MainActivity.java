package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.R;
import com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model.EquipmentStack;

public class MainActivity extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            EquipmentStackListFragment fragment = new EquipmentStackListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, EquipmentStackListFragment.TAG)
                    .commit();
        }
    }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        final RecyclerView equipmentRecyclerView = findViewById(R.id.equipment_stacks_list);
//        equipmentRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager equipmentRecyclerViewLayoutManager = new LinearLayoutManager(this);
//        equipmentRecyclerView.setLayoutManager(equipmentRecyclerViewLayoutManager);
////        RecyclerView.Adapter equipmentRecyclerViewAdapter = new EquipmentStackAdapter()
//        final EquipmentStackAdapter equipmentStackAdapter = new EquipmentStackAdapter(mEquipmentStackClickCallback);
//        equipmentRecyclerView.setAdapter(equipmentStackAdapter);
//
//        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
//                LiveData<List<EquipmentStackEntity>> equipmentStacks = db.equipmentStackDao().loadAll();
//                equipmentStackAdapter.setEquipmentStacks(equipmentStacks);
////                equipmentRecyclerView.setAdapter(new EquipmentStackAdapter(equipmentStacks));
//
////                Log.i("items", "items: " + equipmentStacks.toString());
//            }
//        });


//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    public void show(EquipmentStack equipmentStack) {
        EquipmentStackDetailFragment equipmentStackDetailFragment = EquipmentStackDetailFragment.forEquipmentStack(equipmentStack.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("equipmentStack")
                .replace(R.id.fragment_container, equipmentStackDetailFragment, null)
                .commit();
    }


}
