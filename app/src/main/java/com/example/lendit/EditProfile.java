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

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        userEmail=firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Storing Data...");
                progressDialog.show();
                final String username = userName.getText().toString();
                final String userphone = userPhone.getText().toString();
                final String useradress = userAddress.getText().toString();
                if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(userphone)&&!TextUtils.isEmpty(useradress)&&imageUri!=null) {
                    File newFile = new File(imageUri.getPath());
                    try {
                        compressed = new Compressor(EditProfile.this)
                                .setMaxHeight(125)
                                .setMaxWidth(125)
                                .setQuality(50)
                                .compressToBitmap(newFile);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    compressed.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] thumbData = byteArrayOutputStream.toByteArray();
                    UploadTask image_path = storageReference.child("user_image").child(userEmail + ".jpg").putBytes(thumbData);
                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                storeData(task, username, userphone, useradress);
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(EditProfile.this, "(IMAGE Error) : " + error, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(EditProfile.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(EditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        choseImage();
                    }
                } else {
                    choseImage();
                }
            }
        });
    }

    private void storeData(Task<UploadTask.TaskSnapshot > task, String username, String userphone, String useradress) {
        Task<Uri> download_uri;
        if (task != null) {
            download_uri = task.getResult().getMetadata().getReference().getDownloadUrl();
        } else {
            download_uri = null;
        }
        Map<String, String> userData = new HashMap<>();
        userData.put("userName",username);
        userData.put("userPhone",userphone);
        userData.put("userAddress",useradress);
        userData.put("userImage",download_uri.toString());

        firebaseFirestore.collection("Users").document(userEmail).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfile.this, "User Data is Stored Successfully", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(EditProfile.this, Profile.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(EditProfile.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void choseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(EditProfile.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imageUri = result.getUri();
                userImage.setImageURI(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }
}
