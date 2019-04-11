package com.gratus.ownerapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gratus.ownerapp.Fragment.BottomSheetFragment;
import com.gratus.ownerapp.R;
import com.gratus.ownerapp.model.Dish;
import com.gratus.ownerapp.util.CameraInterface;
import com.gratus.ownerapp.util.PhotoInterface;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

public class DishEditActivity extends AppCompatActivity implements CameraInterface, PhotoInterface {
    private CircleImageView edit_picture;
    private EditText name_et, description_et, price_et, quantity_et;
    private Spinner foodtype_sp;
    private TextView daytime_tv, foodtype_tv;
    private ImageView profileImage;

    private Dish dish;
    private String profilePicture;
    private int selected_foodtype=0;
    public static final int REQUEST_PROFILE_IMAGE = 100;
    private static final String[] foodtypes = {"To choose", "Pizza", "Sushi", "Indian", "Burger", "Pasta", "Vegetarian", "Dessert", "Pasta", "Drink", "Menu", "Other"};
    BottomSheetFragment bottomSheetFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_edit);
        Intent i = getIntent();
        boolean new_dish = i.getBooleanExtra("new_dish",true);
        if (new_dish){
            dish = new Dish();
        } else {
            dish = new Dish(i.getStringArrayListExtra("dish"));
        }

        if (savedInstanceState != null) {
            dish = new Dish( savedInstanceState.getStringArrayList("dish" ));
        }

        edit_picture = findViewById(R.id.edit_dish_picture_button);
        registerForContextMenu(edit_picture);
        name_et = findViewById(R.id.edit_name);
        description_et = findViewById(R.id.edit_description);
        daytime_tv = findViewById(R.id.edit_daytime);
        daytime_tv.setText(i.getStringExtra("daytime"));
        price_et = findViewById(R.id.edit_price);
        quantity_et = findViewById(R.id.edit_quantity);
        foodtype_tv = findViewById(R.id.foodtype_tv);

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
            float price = dish.getPrice();
            if (price != 0) {
                price_et.setText(Float.toString(price));
            }
            String foodtype = dish.getFoodtype();
            for(int j=0; j<foodtypes.length; j++){
                if(foodtypes[j].equals(foodtype)){
                    selected_foodtype = j;
                    break;
                }
            }

            int quantity = dish.getQuantity();
            if (quantity != 0) {
                quantity_et.setText(Integer.toString(quantity));
            }
            String pp_uri = dish.getImageUri();
            loadProfile(pp_uri,profileImage);
        }
        String daytime = dish.getDaytime();
        if (!daytime.equals("")) {
            daytime_tv.setText(daytime);
        }

        foodtype_sp = findViewById(R.id.spinner_foodtype);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(DishEditActivity.this,
                android.R.layout.simple_spinner_item,foodtypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodtype_sp.setAdapter(adapter);

        foodtype_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selected_foodtype = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                parent.setSelection(selected_foodtype);
            }
        });

        edit_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileBottomSheetDialogFragment();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PROFILE_IMAGE) {

            if (resultCode == RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    profilePicture = uri.toString();
                    loadProfile(profilePicture, profileImage);
                    dish.setImageUri(profilePicture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                if(setValidation()) {
                    String name = name_et.getText().toString();
                    dish.setName(name);
                    String description = description_et.getText().toString();
                    dish.setDescription(description);
                    String daytime = daytime_tv.getText().toString();
                    dish.setDaytime(daytime);
                    dish.setFoodtype(foodtypes[selected_foodtype]);
                    String price = price_et.getText().toString();
                    if (price.equals("")) {
                        price = "0";
                    }
                    dish.setPrice(Float.parseFloat(price));
                    String quantity = quantity_et.getText().toString();
                    if (quantity.equals("")) {
                        quantity = "0";
                    }
                    dish.setQuantity(Integer.parseInt(quantity));

                    Intent saveIntent = new Intent();
                    saveIntent.putExtra("button", "save");
                    saveIntent.putExtra("dish", dish.toArrayList());

                    setResult(RESULT_OK, saveIntent);
                    finish();
                }

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

    private boolean setValidation() {
        if((name_et.getText().toString().isEmpty() || name_et.getText().length()<=2)){
            name_et.setError("Please enter the dish name");
            return false;
        }
        if((daytime_tv.getText().toString().isEmpty() || daytime_tv.getText().length()<=4)){
            daytime_tv.setError("Please enter time of availability");
            return false;
        }
        if(selected_foodtype==0){
            foodtype_tv.setError("Please choose a type of food");
            return false;
        }
        if(price_et.getText().toString().isEmpty()){
            price_et.setError("Please enter a valid price");
            return false;
        }
        if(quantity_et.getText().toString().isEmpty()){
            quantity_et.setError("Please enter a valid quantity");
            return false;
        }
        else{
            return true;
        }
    }

    private void loadProfile(String url,ImageView imageView) {
        Glide.with(this).load(url)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    public void showProfileBottomSheetDialogFragment() {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setCameraInterfaceC(DishEditActivity.this);
        bottomSheetFragment.setPhotoInterface(DishEditActivity.this);
    }

    private void launchCameraIntent() {

        Intent intent = new Intent(DishEditActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        bottomSheetFragment.dismiss();
    }

    private void launchGalleryIntent() {

        Intent intent = new Intent(DishEditActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        bottomSheetFragment.dismiss();

    }
    @Override
    public void cameraClicked() {
        launchCameraIntent();
    }

    @Override
    public void photoClicked() {
        launchGalleryIntent();
    }

}