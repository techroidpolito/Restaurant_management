package com.gratus.ownerapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gratus.ownerapp.R;
import com.gratus.ownerapp.adapter.UserProfileNoEditAdapter;
import com.gratus.ownerapp.model.UserProfileAdapterModel;
import com.gratus.ownerapp.model.UserProfileRegistrationModel;


import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileNoEditActivity extends AppCompatActivity {
    private int ADDRESS = 1;
    private int DESCRIPTION = 2;
    private int NAME = 3;
    private int EMAIL = 4;
    private int HEADER = 5;
    private ActionBar toolbar;
    private RelativeLayout exceptionRl,top;
    private RecyclerView userRecyclerView;
    private TextView signUp,restnameTv,phoneTv;
    private UserProfileNoEditAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView topImg;
    private ArrayList<UserProfileRegistrationModel> userProfileRegistrationModels = new ArrayList<>();
    private ArrayList<UserProfileAdapterModel> userProfileAdapterModels = new ArrayList<>();
    public static final int REQUEST_CODE = 1;
    Intent intent;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_no_edit);
        toolbar = getSupportActionBar();
        toolbar.setTitle(getResources().getString(R.string.app_name));
        exceptionRl = findViewById(R.id.exceptionRl);
        top = findViewById(R.id.top);
        topImg = findViewById(R.id.topImg);
        userRecyclerView = findViewById(R.id.userRecyclerView);
        signUp = findViewById(R.id.signUpTv);
        restnameTv = findViewById(R.id.restnameTv);
        phoneTv = findViewById(R.id.phoneTv);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserProfileNoEditActivity.this, UserProfileEditActivity.class);
                if(userProfileRegistrationModels.size()>0) {
                    intent.putExtra("usermodel", userProfileRegistrationModels);
                }
                startActivityForResult(intent, REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        if(userProfileRegistrationModels!=null && userProfileRegistrationModels.size()==0){
            exceptionRl.setVisibility(View.VISIBLE);
            top.setVisibility(View.GONE);
            userRecyclerView.setVisibility(View.GONE);
        }
        else{
            exceptionRl.setVisibility(View.GONE);
            top.setVisibility(View.VISIBLE);
            userRecyclerView.setVisibility(View.VISIBLE);
        }
        if (savedInstanceState != null) {
            savedInstanceState.getSerializable("mod");
        }

    }

    private void setAdapter() {
        exceptionRl.setVisibility(View.GONE);
        top.setVisibility(View.VISIBLE);
        userRecyclerView.setVisibility(View.VISIBLE);
        userProfileAdapterModels = new ArrayList<>();
        if (userProfileRegistrationModels.get(0).getCovePhoto() != null && !userProfileRegistrationModels.get(0).getCovePhoto().isEmpty()) {
            loadCover(userProfileRegistrationModels.get(0).getCovePhoto(),topImg);
        }
        if(userProfileRegistrationModels.get(0).getRestName()!=null && !userProfileRegistrationModels.get(0).getRestName().isEmpty()) {
            restnameTv.setText(userProfileRegistrationModels.get(0).getRestName());
        }
        if(userProfileRegistrationModels.get(0).getPhone()!=null && !userProfileRegistrationModels.get(0).getPhone().isEmpty()) {
            phoneTv.setText(userProfileRegistrationModels.get(0).getPhone());
        }
        if(userProfileRegistrationModels.get(0).getName()!=null && !userProfileRegistrationModels.get(0).getName().isEmpty()) {
            userProfileAdapterModels.add(new UserProfileAdapterModel(NAME, getResources().getString(R.string.name),userProfileRegistrationModels.get(0).getName()));
        }
        if(userProfileRegistrationModels.get(0).getAddress()!=null && !userProfileRegistrationModels.get(0).getAddress().isEmpty()) {
            userProfileAdapterModels.add(new UserProfileAdapterModel(ADDRESS, getResources().getString(R.string.address),userProfileRegistrationModels.get(0).getAddress()));
        }
        if(userProfileRegistrationModels.get(0).getEmail()!=null && !userProfileRegistrationModels.get(0).getEmail().isEmpty()) {
            userProfileAdapterModels.add(new UserProfileAdapterModel(EMAIL,getResources().getString(R.string.email),userProfileRegistrationModels.get(0).getEmail()));
        }
        if(userProfileRegistrationModels.get(0).getDescription()!=null && !userProfileRegistrationModels.get(0).getDescription().isEmpty()) {
            userProfileAdapterModels.add(new UserProfileAdapterModel(DESCRIPTION,getResources().getString(R.string.description),userProfileRegistrationModels.get(0).getDescription()));
        }
        mLayoutManager = new LinearLayoutManager(UserProfileNoEditActivity.this);
        mAdapter = new UserProfileNoEditAdapter(userProfileAdapterModels);
        userRecyclerView.setLayoutManager(mLayoutManager);
        userRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.action_edit:
                intent = new Intent(UserProfileNoEditActivity.this, UserProfileEditActivity.class);
                if(userProfileRegistrationModels.size()>0) {
                    intent.putExtra("usermodel", userProfileRegistrationModels);
                }
                startActivityForResult(intent, REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                userProfileRegistrationModels = (ArrayList<UserProfileRegistrationModel>) bundle.getSerializable("model");
                if(userProfileRegistrationModels.size()>0){
                    setAdapter();
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // some stuff that will happen if there's no result
            }
        }
    }
    private void loadProfile(String url,CircleImageView circleImageView) {
        Glide.with(this).load(url)
                .into(circleImageView);
        circleImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
    private void loadCover(String url, ImageView imageView) {
        Glide.with(this).load(url)
                .into(imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        imageView.setAlpha(0.3f);
        imageView.setBackgroundColor(Color.WHITE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mod", userProfileRegistrationModels);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userProfileRegistrationModels = (ArrayList<UserProfileRegistrationModel>) savedInstanceState.getSerializable("mod");
        if(userProfileRegistrationModels!=null&& userProfileRegistrationModels.size()>0) {
            setAdapter();
        }

    }
}
