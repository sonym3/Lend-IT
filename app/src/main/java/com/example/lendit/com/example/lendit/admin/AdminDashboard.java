package com.example.lendit.com.example.lendit.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lendit.R;

public class AdminDashboard extends AppCompatActivity {

    private Button addItems;
    private Button removeStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        addItems=(Button)findViewById(R.id.addItemsButton);
        removeStudents=(Button)findViewById(R.id.removeStudent);

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminDashboard.this, AdminAddItems.class);
                startActivity(i);
            }
        });


        removeStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
