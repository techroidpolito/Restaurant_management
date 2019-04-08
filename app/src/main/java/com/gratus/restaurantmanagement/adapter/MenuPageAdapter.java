package com.gratus.restaurantmanagement.adapter;

import com.gratus.restaurantmanagement.Fragment.BreakfastPageFragment;
import com.gratus.restaurantmanagement.Fragment.DinerPageFragment;
import com.gratus.restaurantmanagement.Fragment.LunchPageFragment;
import com.gratus.restaurantmanagement.Fragment.PageFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MenuPageAdapter extends FragmentPagerAdapter {
    private static final ArrayList<String> tab_titles = new ArrayList<String>(){{
        add("Breakfast");
        add("Lunch");
        add("Diner");
    }};

    // 2 - Default Constructor
    public MenuPageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        // 4 - Page to return
        switch (position){
            case 0:
                return BreakfastPageFragment.newInstance();
            case 1:
                return LunchPageFragment.newInstance();
            case 2:
                return DinerPageFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tab_titles.get(position);
    }
}
