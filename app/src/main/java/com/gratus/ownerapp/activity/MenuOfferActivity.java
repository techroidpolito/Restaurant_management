package com.gratus.ownerapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.gratus.ownerapp.Fragment.DishBottomFragment;
import com.gratus.ownerapp.Fragment.PageFragment;
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
    static DishBottomFragment dishBottomFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_offer);

        if (savedInstanceState != null){

            int nb_dishes = savedInstanceState.getInt("nb of dishes");
            for(int i=0; i<nb_dishes; i++) {
                Dish d = new Dish( savedInstanceState.getStringArrayList("dish "+i) );
                breakfast_data.add(d);
            }
        } else {
            breakfast_data = new ArrayList<Dish>();
            lunch_data = new ArrayList<Dish>();
            diner_data = new ArrayList<Dish>();
        }

        this.configureViewPagerAndTabs();
        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Check click",v.getId()+" vs "+R.id.dish_more_button);
                if( v.getId()==R.id.dish_more_button ){
                    showDishBottomFragment();
                }
            }
        });

        Log.d("configuration","done");
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

    public static void setData(ArrayList<Dish> arrayList, int fragment_nb){
        switch (fragment_nb){
            case 0:
                breakfast_data = arrayList;
                break;
            case 1:
                lunch_data = arrayList;
                break;
            case 2:
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

    public void showDishBottomFragment() {
        dishBottomFragment = new DishBottomFragment();
        dishBottomFragment.show(getSupportFragmentManager(),dishBottomFragment.getTag());
    }
}
