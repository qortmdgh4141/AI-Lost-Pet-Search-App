package com.park.proto_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BoardFrag boardFrag;
    ArcFrag arcFrag;
    AddFrag addFrag;
    ProFrag proFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        replaceFragment(new BoardFrag());


        boardFrag = new BoardFrag();
        arcFrag = new ArcFrag();
        addFrag = new AddFrag();
        proFrag = new ProFrag();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, boardFrag).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, boardFrag).commit();
                        return true;

                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, arcFrag).commit();
                        return true;

                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, addFrag).commit();
                        return true;

                    case R.id.fourth_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, proFrag).commit();
                        return true;
                }
                return false;
            }
        });
    }

}