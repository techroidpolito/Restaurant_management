package com.gratus.ownerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import com.gratus.ownerapp.Fragment.BottomSheetFragment;
import com.gratus.ownerapp.R;
import com.gratus.ownerapp.model.UserProfileRegistrationModel;
import com.gratus.ownerapp.util.CameraInterface;
import com.gratus.ownerapp.util.PhotoInterface;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileEditActivity extends AppCompatActivity implements CameraInterface, PhotoInterface {
    private ActionBar toolbar;
    private MenuItem save;
    private RelativeLayout top;
    private ImageView bg_userImg,topImg;
    private EditText nameEt,addressEt,mailEt,phoneEt,sDesEt,restnameEt;
    private ArrayList<UserProfileRegistrationModel> userProfileRegistrationModels = new ArrayList<>();
    private String coverImg;
    boolean pressed =false;
    public static final int REQUEST_PROFILE_IMAGE = 100;
    public static final int REQUEST_BG_IMAGE = 101;
    BottomSheetFragment bottomSheetFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        toolbar = getSupportActionBar();
        toolbar.setTitle(getResources().getString(R.string.app_name));
        top = findViewById(R.id.top);
        topImg = findViewById(R.id.topImg);
        bg_userImg = findViewById(R.id.bg_userImg);
        nameEt = findViewById(R.id.nameEt);
        addressEt = findViewById(R.id.addressEt);
        mailEt = findViewById(R.id.mailEt);
        phoneEt = findViewById(R.id.phoneEt);
        sDesEt = findViewById(R.id.sDesEt);
        restnameEt = findViewById(R.id.restnameEt);

        if(getIntent().getSerializableExtra("usermodel")!=null){
                userProfileRegistrationModels = (ArrayList<UserProfileRegistrationModel>) getIntent().getSerializableExtra("usermodel");
        }
        if(userProfileRegistrationModels.size()>0){
            if (userProfileRegistrationModels.get(0).getCovePhoto() != null && !userProfileRegistrationModels.get(0).getCovePhoto().isEmpty()) {
                loadCover(userProfileRegistrationModels.get(0).getCovePhoto(),topImg);
                coverImg = userProfileRegistrationModels.get(0).getCovePhoto();
            }
            if(userProfileRegistrationModels.get(0).getName()!=null && !userProfileRegistrationModels.get(0).getName().isEmpty()) {
                nameEt.setText(userProfileRegistrationModels.get(0).getName());
            }
            if(userProfileRegistrationModels.get(0).getPhone()!=null && !userProfileRegistrationModels.get(0).getPhone().isEmpty()) {
                phoneEt.setText(userProfileRegistrationModels.get(0).getPhone());
            }
            if(userProfileRegistrationModels.get(0).getAddress()!=null && !userProfileRegistrationModels.get(0).getAddress().isEmpty()) {
                addressEt.setText(userProfileRegistrationModels.get(0).getAddress());
            }
            if(userProfileRegistrationModels.get(0).getEmail()!=null && !userProfileRegistrationModels.get(0).getEmail().isEmpty()) {
                mailEt.setText(userProfileRegistrationModels.get(0).getEmail());
            }
            if(userProfileRegistrationModels.get(0).getDescription()!=null && !userProfileRegistrationModels.get(0).getDescription().isEmpty()) {
                sDesEt.setText(userProfileRegistrationModels.get(0).getDescription());
            }
            if(userProfileRegistrationModels.get(0).getRestName()!=null && !userProfileRegistrationModels.get(0).getRestName().isEmpty()) {
                restnameEt.setText(userProfileRegistrationModels.get(0).getRestName());
            }
        }
        bg_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed =false;
                showCoverBottomSheetDialogFragment();
            }
        });
        if (savedInstanceState != null) {
            savedInstanceState.getString("profile", null);
            savedInstanceState.getString("cover", null);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_save:
                setSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setSave() {
        userProfileRegistrationModels = new ArrayList<>();
        if(setValidation()) {
            userProfileRegistrationModels.add(new UserProfileRegistrationModel(
                    nameEt.getText().toString(),
                    phoneEt.getText().toString(),
                    mailEt.getText().toString(),
                    addressEt.getText().toString(),
                    sDesEt.getText().toString(),
                    restnameEt.getText().toString(),
                    coverImg
            ));
            if (userProfileRegistrationModels.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("model", userProfileRegistrationModels);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private boolean setValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if((restnameEt.getText().toString().isEmpty() || restnameEt.getText().length()<=2)){
            restnameEt.setError("Please enter the restaurant name");
            return false;
        }
        if((nameEt.getText().toString().isEmpty() || nameEt.getText().length()<=2)){
            nameEt.setError("Please enter the name");
            return false;
        }
        if((addressEt.getText().toString().isEmpty() || addressEt.getText().length()<=5)){
            addressEt.setError("Please enter the vaild address");
            return false;
        }
        if((mailEt.getText().toString().isEmpty() || ! mailEt.getText().toString().matches(emailPattern))){
            mailEt.setError("Please enter the vaild email address");
            return false;
        }
        if((phoneEt.getText().toString().isEmpty()|| phoneEt.getText().length()<10)){
            phoneEt.setError("Please enter the vaild phone number");
            return false;
        }
        else{
            return true;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        save = menu.findItem(R.id.action_save);
        save.setVisible(true);
        return true;
    }
    public void showProfileBottomSheetDialogFragment() {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setCameraInterfaceC(UserProfileEditActivity.this);
        bottomSheetFragment.setPhotoInterface(UserProfileEditActivity.this);
    }
    public void showCoverBottomSheetDialogFragment() {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        bottomSheetFragment.setCameraInterfaceC(UserProfileEditActivity.this);
        bottomSheetFragment.setPhotoInterface(UserProfileEditActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PROFILE_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == REQUEST_BG_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    coverImg = uri.toString();
                    loadCover(coverImg,topImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void cameraClicked() {
        launchCameraIntent();
    }

    @Override
    public void photoClicked() {
        launchGalleryIntent();
    }

    private void launchCameraIntent() {

        Intent intent = new Intent(UserProfileEditActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        if(pressed==true) {
            startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        }
        else{
            startActivityForResult(intent, REQUEST_BG_IMAGE);
        }
        bottomSheetFragment.dismiss();
    }


    private void launchGalleryIntent() {

        Intent intent = new Intent(UserProfileEditActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        if(pressed==true){
        startActivityForResult(intent, REQUEST_PROFILE_IMAGE);
        }
        else{
        startActivityForResult(intent, REQUEST_BG_IMAGE);
        }
        bottomSheetFragment.dismiss();

    }
    private void loadProfile(String url,CircleImageView circleImageView) {
        Glide.with(this).load(url)
                .into(circleImageView);
        circleImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
    private void loadCover(String url,ImageView imageView) {
        Glide.with(this).load(url)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        imageView.setAlpha(0.3f);
        imageView.setBackgroundColor(Color.WHITE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cover", coverImg);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        coverImg = savedInstanceState.getString("cover", coverImg);
        if(coverImg!=null) {
            loadCover(coverImg, topImg);
        }
    }
}
