package com.example.lendit.com.example.lendit.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lendit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WaitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_list);



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_waitlist );
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(WaitList.this, Dashboard.class);
                        startActivity(a);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_cart:
                        Intent c = new Intent(WaitList.this, Orders.class);
                        startActivity(c);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        Intent b = new Intent(WaitList.this, Profile.class);
                        startActivity(b);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_waitlist:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        Intent c = new Intent(WaitList.this,Dashboard.class);
        startActivity(c);
        overridePendingTransition(0,0);
    }
}
