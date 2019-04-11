package com.gratus.ownerapp.model;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.gratus.ownerapp.R;

import java.util.ArrayList;

public class Dish {
    private String imageUri, name, description, daytime, foodtype;
    private int quantity;
    private float price;
    private boolean already_filled;

    public Dish(){
        this.imageUri = Uri.parse("android.resource://com.gratus.restaurantmanagement/" + R.drawable.food_icon).toString();
        this.name = "";
        this.description = "";
        this.daytime = "";
        this.foodtype = "";
        this.quantity = 0;
        this.price = 0;
        this.already_filled = false;
    }

    public Dish(ArrayList<String> array){
        this.imageUri = array.get(0);
        this.name = array.get(1);
        this.description = array.get(2);
        this.daytime = array.get(3);
        this.foodtype = array.get(4);
        this.quantity = Integer.parseInt(array.get(5));
        this.price = Float.parseFloat(array.get(6));
        this.already_filled = true;
    }

    public String getImageUri(){
        return this.imageUri;
    }

    public void setImageUri(String uri){
        this.imageUri = uri;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name_to_set){
        this.name = name_to_set;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public float getPrice(){
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDaytime(){
        return this.daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public String getFoodtype(){
        return this.foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public boolean isAlready_filled() {
        return already_filled;
    }

    public ArrayList toArrayList(){
        ArrayList<String> array = new ArrayList();
        array.add(this.imageUri);
        array.add(this.name);
        array.add(this.description);
        array.add(this.daytime);
        array.add(this.foodtype);
        array.add(""+this.quantity);
        array.add(""+this.price);
        return array;
    }
}
