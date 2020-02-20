package com.example.lendit.com.example.lendit.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lendit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemSingleAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_single_admin);

        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonObject");
        try {
            System.out.println("THis is single click item " + jsonArray);
            JSONObject jsonObject = new JSONObject(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
