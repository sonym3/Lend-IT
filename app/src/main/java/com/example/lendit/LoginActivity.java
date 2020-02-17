package com.example.lendit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private String strEmail;
    private String strPass;
    private Button btnLogin;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        email=(EditText)findViewById(R.id.idemail);
       password=(EditText)findViewById(R.id.idpassword);
       btnLogin=(Button)findViewById(R.id.btnlogin);
       btnRegister = (Button)findViewById(R.id.btnregister);



      btnRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this, Register.class);
               startActivity(intent);
           }
       });
      btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               strEmail=email.getText().toString();
               strPass=password.getText().toString();

               if (strEmail.isEmpty()) {
                   email.setError("Email");
                   email.requestFocus();
               }
               else if (strPass.isEmpty()){
                   password.setError("Password");
                   password.requestFocus();
               }
               else if(!(strPass.isEmpty() && strEmail.isEmpty())){

               }
               else
                   Toast.makeText(LoginActivity.this,"Error occured, Please try later", Toast.LENGTH_LONG).show();
           }
       });
    }

}
