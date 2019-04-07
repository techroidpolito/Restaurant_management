package com.gratus.restaurantmanagement.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gratus.restaurantmanagement.R;
import com.gratus.restaurantmanagement.model.Dish;

import androidx.recyclerview.widget.RecyclerView;


class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private MyRecyclerAdapter mAdapter;
    TextView name, description, price, quantity;
    ImageView image;
    Button more;
    int position;
    Dish current;

    public MyViewHolder(View itemView, MyRecyclerAdapter adapter) {
        super(itemView);
        name = itemView.findViewById(R.id.dish_name);
        description = itemView.findViewById(R.id.dish_description);
        price = itemView.findViewById(R.id.dish_price);
        quantity = itemView.findViewById(R.id.dish_quantity);
        image = itemView.findViewById(R.id.dish_picture);
        more = itemView.findViewById(R.id.more_button);
        this.mAdapter = adapter;
    }

    public void setData(Dish current) {
        this.name.setText(current.getName());
        this.description.setText(current.getDescription());
        this.price.setText(""+current.getPrice());
        this.quantity.setText(""+current.getQuantity());
        this.image.setImageDrawable(Drawable.createFromPath("@drawable/logo"));
    }

    public void setListeners(){
        more.setOnClickListener(MyViewHolder.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_button:
                //pop-up fragment: activity and layout to define
                break;
        }
    }

}