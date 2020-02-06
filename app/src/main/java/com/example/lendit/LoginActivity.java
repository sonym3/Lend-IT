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
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mAuth = FirebaseAuth.getInstance();


        email=(EditText)findViewById(R.id.idemail);
       password=(EditText)findViewById(R.id.idpassword);
       btnLogin=(Button)findViewById(R.id.btnlogin);
       btnRegister = (Button)findViewById(R.id.btnregister);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this,Dashboard.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please login",Toast.LENGTH_LONG).show();

                }
            }
        };

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
                   mAuth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Intent i = new Intent(LoginActivity.this,Dashboard.class);
                               startActivity(i);
                           } else {
                               // If sign in fails, display a message to the user.
                               Toast.makeText(LoginActivity.this, "Authentication failed.",
                                       Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
               else
                   Toast.makeText(LoginActivity.this,"Error occured, Please try later", Toast.LENGTH_LONG).show();
           }
       });
    }

}
