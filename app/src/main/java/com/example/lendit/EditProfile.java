package com.example.lendit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class EditProfile extends AppCompatActivity {
    private ImageView userImage;
    private EditText userName, userPhone, userAddress;
    private Button submit;
    private ProgressDialog progressDialog;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String user_id;
    private String userEmail;
    private Bitmap compressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile );
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(EditProfile.this,Dashboard.class);
                        startActivity(a);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_cart:
                        Intent c = new Intent(EditProfile.this,Cart.class);
                        startActivity(c);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        Intent x = new Intent(EditProfile.this,Profile.class);
                        startActivity(x);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_waitlist:
                        Intent b = new Intent(EditProfile.this, WaitList.class);
                        startActivity(b);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        progressDialog = new ProgressDialog(this);

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userPhone = findViewById(R.id.user_phone);
        userAddress = findViewById(R.id.user_address);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Storing Data...");
                progressDialog.show();
                final String username = userName.getText().toString();
                final String userphone = userPhone.getText().toString();
                final String useradress = userAddress.getText().toString();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(userphone)&&!TextUtils.isEmpty(useradress)&&imageUri!=null) {

                }
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
