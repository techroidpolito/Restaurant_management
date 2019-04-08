package com.gratus.restaurantmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.gratus.restaurantmanagement.R;
import com.gratus.restaurantmanagement.adapter.ItemListAdapter;
import com.gratus.restaurantmanagement.adapter.MenuPageAdapter;
import com.gratus.restaurantmanagement.adapter.MyRecyclerAdapter;
import com.gratus.restaurantmanagement.model.Dish;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MenuOfferActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private ArrayList<Dish> breakfast_data;
    private ArrayList<Dish> lunch_data;
    private ArrayList<Dish> diner_data;
    private FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_offer);
        breakfast_data = new ArrayList<Dish>();
        lunch_data = new ArrayList<Dish>();
        diner_data = new ArrayList<Dish>();

        //for now, only breakfast is saved
        if (savedInstanceState != null){
            int nb_dishes = savedInstanceState.getInt("nb of dishes");
            for(int i=0; i<nb_dishes; i++) {
                Dish d = new Dish( savedInstanceState.getStringArrayList("dish "+i) );
                breakfast_data.add(d);
            }
        }

        //mRecyclerView = findViewById(R.id.dishes_list);
        //mAdapter = new MyRecyclerAdapter(this, data);
        //mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.configureViewPagerAndTabs();

        Log.d("configuration","done");
    }

    private void configureViewPagerAndTabs(){
        // Get ViewPager from layout
        ViewPager pager = findViewById(R.id.viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new MenuPageAdapter(getSupportFragmentManager()) {
        });


        // Get TabLayout from layout
        TabLayout tabs = findViewById(R.id.menus_tablayout);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Iterator<Dish> it = breakfast_data.iterator();
        int index = 0;
        while (it.hasNext()) {
            ArrayList al = it.next().toArrayList();
            outState.putStringArrayList("dish "+String.valueOf(index), al);
            index += 1;
        }
        //outState.putInt("nb of dishes", mAdapter.getItemCount());
        outState.putInt("nb of dishes", index);
        //Log.d("check the nb of dishes", mAdapter.getItemCount()+" "+index);
    }
}
