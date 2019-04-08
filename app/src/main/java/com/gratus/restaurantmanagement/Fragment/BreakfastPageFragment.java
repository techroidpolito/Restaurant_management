package com.gratus.restaurantmanagement.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gratus.restaurantmanagement.R;
import com.gratus.restaurantmanagement.activity.DishEditActivity;
import com.gratus.restaurantmanagement.model.Dish;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class BreakfastPageFragment extends Fragment {
    // 1 - Create keys for our Bundle
    //private static final String KEY_POSITION="position";
    private ArrayList<Dish> breakfast_data;

    public BreakfastPageFragment() { }


    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    public static BreakfastPageFragment newInstance() {

        // 2.1 Create new fragment
        BreakfastPageFragment frag = new BreakfastPageFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        frag.setArguments(args);

        return(frag);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 3 - Get layout of PageFragment
        View result = inflater.inflate(R.layout.breakfast_fragment_page, container, false);

        // 4 - Get widgets from layout and serialise it
        RelativeLayout rootView= result.findViewById(R.id.fragment_page_rootview);
        RecyclerView rv = result.findViewById(R.id.breakfast_rv);
        FloatingActionButton fab = result.findViewById(R.id.breakfast_fab);

        // 5 - Get data from Bundle (created in method newInstance)
        //int position = getArguments().getInt(KEY_POSITION, -1);

        // 6 - Update widgets with it
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fab_intent = new Intent(getActivity(), DishEditActivity.class);
                fab_intent.putExtra("new dish", true);
                Log.d("fab", "clicked");
                startActivity(fab_intent);
            }
        });

        Log.d(getClass().getSimpleName(), "onCreateView called for Breakfast fragment");

        return result;
    }

}
