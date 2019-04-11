package com.gratus.ownerapp.adapter;

import com.gratus.ownerapp.Fragment.PageFragment;

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
        return(tab_titles.size()); // 3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(this.getPageTitle(position), position);
    }

    @Override
    public String getPageTitle(int position){
        return tab_titles.get(position);
    }
}
