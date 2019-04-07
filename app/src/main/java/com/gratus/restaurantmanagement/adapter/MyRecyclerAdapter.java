package com.gratus.restaurantmanagement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gratus.restaurantmanagement.R;
import com.gratus.restaurantmanagement.model.Dish;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Dish> mData;
    private LayoutInflater mInflater;

    public MyRecyclerAdapter(Context context, ArrayList<Dish> data) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dish_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("MyRecyclerAdapter", "onBindViewHolder "+position);
        Dish currentDish = mData.get(position);
        holder.setData(currentDish);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(int position, Dish dish){
        mData.add(position, dish);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mData.size());
    }

    public void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mData.size());
    }

}


