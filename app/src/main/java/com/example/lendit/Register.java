package com.example.lendit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText registerEmail;
    private EditText registerPass;
    private Button registerButton;

    private String email;
    private String pass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail=(EditText)findViewById(R.id.idemailregister);
        registerPass=(EditText)findViewById(R.id.idpasswordregister);
        registerButton=(Button) findViewById(R.id.idregisterhere);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=registerEmail.getText().toString();
                pass=registerPass.getText().toString();

                if (email.isEmpty()) {
                    registerEmail.setError("Email");
                    registerEmail.requestFocus();
                }
                else if (pass.isEmpty()){
                    registerPass.setError("Password");
                    registerPass.requestFocus();
                }
                else if(!(email.isEmpty() && email.isEmpty())){

                }
            }
        });


    }
}
