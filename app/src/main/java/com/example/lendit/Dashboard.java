package com.example.lendit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home );
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_cart:
                        Intent a = new Intent(Dashboard.this,Cart.class);
                        startActivity(a);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        Intent b = new Intent(Dashboard.this, Profile.class);
                        startActivity(b);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_waitlist:
                        Intent c = new Intent(Dashboard.this,WaitList.class);
                        startActivity(c);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}