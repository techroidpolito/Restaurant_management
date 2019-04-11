package com.gratus.ownerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gratus.ownerapp.R;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button profileButton;
    Button menusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        profileButton = findViewById(R.id.profile_button);
        menusButton = findViewById(R.id.menus_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_intent = new Intent(DashboardActivity.this, UserProfileNoEditActivity.class);
                startActivity(profile_intent);
            }
        });

        menusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menus_intent = new Intent(DashboardActivity.this, MenuOfferActivity.class);
                startActivity(menus_intent);
            }
        });


    }
}
