package com.example.lendit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Profile extends AppCompatActivity {
    private StorageReference storageReference;
    ImageView ivuser;
    TextView tvname;
    TextView tvphone;
    TextView tvaddress;
    Button editProfile;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile=(Button)findViewById(R.id.editProfileButton);
        ivuser=(ImageView) findViewById(R.id.ImageVIew_user_image);
        tvname=(TextView)findViewById(R.id.textView_user_name);
        tvphone=(TextView)findViewById(R.id.textView_user_phone);
        tvaddress=(TextView)findViewById(R.id.textView_user_address);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("On your mark..");
        progressDialog.show();


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,EditProfile.class);
                startActivity(i);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile );
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(Profile.this,Dashboard.class);
                        startActivity(a);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_cart:
                        Intent c = new Intent(Profile.this,Cart.class);
                        startActivity(c);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        break;
                    case R.id.navigation_waitlist:
                        Intent b = new Intent(Profile.this, WaitList.class);
                        startActivity(b);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        Intent c = new Intent(Profile.this,Dashboard.class);
        startActivity(c);
        overridePendingTransition(0,0);
    }


}
