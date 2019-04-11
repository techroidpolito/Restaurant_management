package com.gratus.ownerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gratus.ownerapp.R;
import com.gratus.ownerapp.activity.DishEditActivity;
import com.gratus.ownerapp.activity.MenuOfferActivity;
import com.gratus.ownerapp.model.Dish;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private ArrayList<Dish> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String daytime;
    static int menu_request = 2;

    public MyRecyclerAdapter(Context context, ArrayList<Dish> data, String daytime) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.daytime = daytime;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, description, price, quantity;
        ImageView image;
        ImageButton edit, delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dish_name);
            description = itemView.findViewById(R.id.dish_description);
            price = itemView.findViewById(R.id.dish_price);
            quantity = itemView.findViewById(R.id.dish_quantity);
            image = itemView.findViewById(R.id.dish_picture);
            edit = itemView.findViewById(R.id.dish_edit_button);
            delete = itemView.findViewById(R.id.dish_delete_button);
        }

        public void setData(Dish current) {
            this.name.setText(current.getName());
            this.description.setText(current.getDescription());
            this.price.setText(Float.toString(current.getPrice()) + " €");
            this.quantity.setText("Quantity: " + Integer.toString(current.getQuantity()));
            this.image.setImageURI(Uri.parse(current.getImageUri()));
        }

        public void setListeners() {
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dish_edit_button:
                    Intent edit_intent = new Intent(context, DishEditActivity.class);
                    edit_intent.putExtra("new_dish", false);
                    edit_intent.putExtra("daytime", daytime);
                    edit_intent.putStringArrayListExtra("dish",mData.get(getAdapterPosition()).toArrayList());
                    ((Activity) context).startActivityForResult(edit_intent, menu_request);
                    break;
                case R.id.dish_delete_button:
                    removeItem(getAdapterPosition());
                    break;
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dish_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
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

    public void addItem(Dish dish){
        int last_position = this.getItemCount();
        mData.add(dish);
        MenuOfferActivity.setData(mData, daytime);
        notifyItemInserted(last_position);
        notifyItemRangeChanged(last_position,mData.size());
    }

    public void removeItem(int position){
        mData.remove(position);
        MenuOfferActivity.setData(mData, daytime);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mData.size());
    }

}


