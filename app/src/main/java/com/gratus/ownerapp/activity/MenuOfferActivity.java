package com.gratus.ownerapp.activity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.gratus.ownerapp.R;
import com.gratus.ownerapp.adapter.MenuPageAdapter;
import com.gratus.ownerapp.model.Dish;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MenuOfferActivity extends AppCompatActivity {

    private static ArrayList<Dish> breakfast_data;
    private static ArrayList<Dish> lunch_data;
    private static ArrayList<Dish> diner_data;
    private FloatingActionButton fab;
    private ViewPager pager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_offer);

        if (savedInstanceState != null){

            breakfast_data.clear();
            int nb_dishes_b = savedInstanceState.getInt("nb_dishes_b");
            for(int i=0; i<nb_dishes_b; i++) {
                Dish d = new Dish( savedInstanceState.getStringArrayList("breakfast_dish"+i) );
                breakfast_data.add(d);
            }

            lunch_data.clear();
            int nb_dishes_l = savedInstanceState.getInt("nb_dishes_l");
            for(int i=0; i<nb_dishes_l; i++) {
                Dish d = new Dish(savedInstanceState.getStringArrayList("lunch_dish" + i));
                lunch_data.add(d);
            }

            diner_data.clear();
            int nb_dishes_d = savedInstanceState.getInt("nb_dishes_d");
            for(int i=0; i<nb_dishes_d; i++) {
                Dish d = new Dish(savedInstanceState.getStringArrayList("diner_dish" + i));
                diner_data.add(d);
            }

        } else {
            breakfast_data = new ArrayList<Dish>();
            lunch_data = new ArrayList<Dish>();
            diner_data = new ArrayList<Dish>();
        }

        this.configureViewPagerAndTabs();
    }

    private void configureViewPagerAndTabs(){
        // Get ViewPager from layout
        pager = findViewById(R.id.viewpager);
        // Set Adapter PageAdapter and glue it together
        pager.setAdapter(new MenuPageAdapter(getSupportFragmentManager()) {
        });


        // Get TabLayout from layout
        TabLayout tabs = findViewById(R.id.menus_tablayout);
        // Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        Iterator<Dish> it = breakfast_data.iterator();
        int indexb = 0;
        while (it.hasNext()) {
            ArrayList al = it.next().toArrayList();
            outState.putStringArrayList("breakfast_dish"+String.valueOf(indexb), al);
            indexb += 1;
        }
        outState.putInt("nb_dishes_b", indexb);

        Iterator<Dish> it2 = lunch_data.iterator();
        int indexl = 0;
        while (it2.hasNext()) {
            ArrayList al = it2.next().toArrayList();
            outState.putStringArrayList("lunch_dish"+String.valueOf(indexl), al);
            indexl += 1;
        }
        outState.putInt("nb_dishes_l", indexl);

        Iterator<Dish> it3 = diner_data.iterator();
        int indexd = 0;
        while (it3.hasNext()) {
            ArrayList al = it3.next().toArrayList();
            outState.putStringArrayList("diner_dish"+String.valueOf(indexd), al);
            indexd += 1;
        }
        outState.putInt("nb_dishes_d", indexd);
    }

    public static void setData(ArrayList<Dish> arrayList, String daytime){
        switch (daytime){
            case "Breakfast":
                breakfast_data = arrayList;
                break;
            case "Lunch":
                lunch_data = arrayList;
                break;
            case "Diner":
                diner_data = arrayList;
                break;
            default:
                break;
        }
    }

    public static ArrayList<Dish> getData(int fragment_nb){
        switch (fragment_nb){
            case 0:
                return breakfast_data;
            case 1:
                return lunch_data;
            case 2:
                return diner_data;
            default:
                return null;
        }
    }

}
