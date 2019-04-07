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
    private ArrayList<Dish> data;
    private FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_offer);
        data = new ArrayList<Dish>();

        if (savedInstanceState != null){
            int nb_dishes = savedInstanceState.getInt("nb of dishes");
            for(int i=0; i<nb_dishes; i++) {
                Dish d = new Dish( savedInstanceState.getStringArrayList("dish "+i) );
                data.add(d);
            }
        }

        mRecyclerView = findViewById(R.id.dishesRecyclerView);
        mAdapter = new MyRecyclerAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.configureViewPagerAndTabs();

        Log.d("configuration","done");
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fab_intent = new Intent(MenuOfferActivity.this, DishEditActivity.class);
                Log.d("fab","clicked");
                startActivity(fab_intent);
            }
        });
    }

    private void configureViewPagerAndTabs(){
        // Get ViewPager from layout
        ViewPager pager = findViewById(R.id.viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new MenuPageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager)) {
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
        Iterator<Dish> it = data.iterator();
        int index = 0;
        while (it.hasNext()) {
            ArrayList al = it.next().toArrayList();
            outState.putStringArrayList("dish "+String.valueOf(index), al);
            index += 1;
        }
        outState.putInt("nb of dishes", mAdapter.getItemCount());
        Log.d("check the nb of dishes", mAdapter.getItemCount()+" "+index);
    }
}
