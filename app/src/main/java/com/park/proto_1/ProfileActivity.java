package com.park.proto_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavigationView.postDelayed(() -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.main) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if (itemId == R.id.api) {
                        startActivity(new Intent(getApplicationContext(), Api_main.class));
                    }else if (itemId == R.id.profile) {
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                    finish();
                }, 100);
                return true;
            };
        });

    }
}