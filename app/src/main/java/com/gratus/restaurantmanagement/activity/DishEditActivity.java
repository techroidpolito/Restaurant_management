package com.gratus.restaurantmanagement.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gratus.restaurantmanagement.R;
import com.gratus.restaurantmanagement.model.Dish;

import java.io.File;
import java.io.FileNotFoundException;

import androidx.appcompat.app.AppCompatActivity;

public class DishEditActivity extends AppCompatActivity {
    ImageButton edit_picture;
    EditText name_et;
    EditText description_et;
    EditText daytime_et;
    EditText foodtype_et;
    EditText price_et;
    EditText quantity_et;
    ImageView profileImage;

    Dish dish;
    private Uri imageUri;
    static int picture_request_code = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_edit);
        Intent i = getIntent();
        dish = new Dish(i.getStringArrayListExtra("dish"));

        if (savedInstanceState != null) {
            dish = new Dish( savedInstanceState.getStringArrayList("dish" ));
        }

        edit_picture = findViewById(R.id.edit_dish_picture_button);
        registerForContextMenu(edit_picture);
        name_et = findViewById(R.id.edit_name);
        description_et = findViewById(R.id.edit_description);
        daytime_et = findViewById(R.id.edit_daytime);
        foodtype_et = findViewById(R.id.edit_food_type);
        price_et = findViewById(R.id.edit_price);
        quantity_et = findViewById(R.id.edit_quantity);

        profileImage = findViewById(R.id.edit_profile_picture);

        if (dish.isAlready_filled()){
            String username = dish.getName();
            if (!username.equals("")) {
                name_et.setText(username);
            }
            String description = dish.getDescription();
            if (!description.equals("")) {
                description_et.setText(description);
            }
            String daytime = dish.getDaytime();
            if (!daytime.equals("")) {
                daytime_et.setText(daytime);
            }
            String food_type = dish.getFoodtype();
            if (!food_type.equals("")) {
                foodtype_et.setText(food_type);
            }
            float price = dish.getPrice();
            if (price != 0) {
                price_et.setText(Float.toString(price));
            }

            int quantity = dish.getQuantity();
            if (quantity != 0) {
                quantity_et.setText(Integer.toString(quantity));
            }
            Uri pp_uri = Uri.parse(dish.getImageUri());
            setProfilePicture(pp_uri);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == picture_request_code) {
                Uri targetUri = data.getData();
                setProfilePicture(targetUri);
                dish.setImageUri(targetUri.toString());
            }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("dish",dish.toArrayList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topmenu_save, menu);
        Log.i("onCreateOptionsMenu","ok in EditingActivity");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_cancel:
                Intent cancelIntent = new Intent();
                cancelIntent.putExtra("button", "cancel");
                setResult(RESULT_OK, cancelIntent);
                finish();

            case R.id.action_save:
                String name = name_et.getText().toString();
                dish.setName(name);
                String description = description_et.getText().toString();
                dish.setDescription(description);
                String daytime = daytime_et.getText().toString();
                dish.setDaytime(daytime);
                String foodtype = foodtype_et.getText().toString();
                dish.setFoodtype(foodtype);
                String price = price_et.getText().toString();
                dish.setPrice(Float.parseFloat(price));
                String quantity = quantity_et.getText().toString();
                dish.setQuantity(Integer.parseInt(quantity));

                Intent saveIntent = new Intent();
                saveIntent.putExtra("button", "save");
                saveIntent.putExtra("dish", dish.toArrayList());

                setResult(RESULT_OK, saveIntent);
                finish();

            default:
                return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_picture_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_camera:
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(camera_intent, picture_request_code);
            case R.id.context_gallery:
                Intent gallery_intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery_intent, picture_request_code);
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void setProfilePicture(Uri picture_uri){
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(picture_uri));
            profileImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}