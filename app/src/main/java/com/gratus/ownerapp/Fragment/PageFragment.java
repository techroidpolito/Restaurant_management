package com.gratus.ownerapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gratus.ownerapp.R;
import com.gratus.ownerapp.activity.DishEditActivity;
import com.gratus.ownerapp.activity.MenuOfferActivity;
import com.gratus.ownerapp.adapter.MyRecyclerAdapter;
import com.gratus.ownerapp.model.Dish;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class PageFragment extends Fragment {
    // 1 - Create keys for our Bundle
    private static final String KEY_DAYTIME="daytime";
    private static final String KEY_POSITION="position";
    private ArrayList<Dish> data;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private FloatingActionButton mFab;
    static int new_menu_request = 101;

    public PageFragment() {    }

    // 2 - Method that will create a new instance of PageFragment, and add data to its bundle.
    public static PageFragment newInstance(String daytime, int position) {

        // 2.1 Create new fragment
        PageFragment frag = new PageFragment();

        // 2.2 Create bundle and add it some data
        Bundle args = new Bundle();
        args.putString(KEY_DAYTIME, daytime);
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return(frag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt(KEY_POSITION);
        data = MenuOfferActivity.getData(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 3 - Get layout of PageFragment
        View result = inflater.inflate(R.layout.fragment_page, container, false);

        // 4 - Get widgets from layout and serialise it
        RelativeLayout rootView= result.findViewById(R.id.fragment_page_rootview);
        mFab = result.findViewById(R.id.fab);
        mRecyclerView = result.findViewById(R.id.fragment_rv);
        final String daytime = getArguments().getString(KEY_DAYTIME);
        mAdapter = new MyRecyclerAdapter(getContext(), data, daytime);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        // 6 - Update widgets with it
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fab_intent = new Intent(getActivity(), DishEditActivity.class);
                fab_intent.putExtra("new_dish", true);
                fab_intent.putExtra("daytime", daytime);
                Log.d("fab", "clicked");
                startActivityForResult(fab_intent, new_menu_request);
            }
        });

        Log.d(getClass().getSimpleName(), "onCreateView called for "+daytime);

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == new_menu_request) {
            String action_type = data.getStringExtra("button");
            if (action_type.equals("save")){
                Dish new_dish = new Dish( data.getStringArrayListExtra("dish") );
                mAdapter.addItem(new_dish);
                Log.d("item added","position "+mAdapter.getItemCount());
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the fragment's state
        int position = getArguments().getInt(KEY_POSITION);
        //MenuOfferActivity.setData(data, position);
        Log.d("key",getArguments().getString(KEY_DAYTIME)+position);
    }

}
