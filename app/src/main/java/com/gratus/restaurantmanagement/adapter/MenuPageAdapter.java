package com.gratus.restaurantmanagement.adapter;

import com.gratus.restaurantmanagement.Fragment.PageFragment;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MenuPageAdapter extends FragmentPagerAdapter {
    private static final ArrayList<String> tab_titles = new ArrayList<String>(){{
        add("Breakfast");
        add("Lunch");
        add("Breakfast");
    }};

    // 1 - Array of colors that will be passed to PageFragment
    private int[] colors;

    // 2 - Default Constructor
    public MenuPageAdapter(FragmentManager mgr, int[] colors) {
        super(mgr);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return(3); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        // 4 - Page to return
        return(PageFragment.newInstance(position, this.colors[position]));
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tab_titles.get(position);
    }
}
