package com.example.lendit.com.example.lendit.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lendit.R;
import com.example.lendit.com.example.lendit.admin.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private EditText email;
    private EditText mobile;
    private EditText Id;
    private Button registerButton;
    private String ipAddress="192.168.1.182";

    private String strEmail;
    private String strPass;
    private String strName;
    private String strMobile;
    private String strSex;
    private String strId;
    private String message;
    private String userStatus;
    RadioButton rMale,rFemale;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=(EditText)findViewById(R.id.EditTextName);
        password=(EditText)findViewById(R.id.EditTextPassword);
        email=(EditText)findViewById(R.id.EditTextEmail);
        mobile=(EditText)findViewById(R.id.EditTextMobile);
        registerButton=(Button) findViewById(R.id.idregisterhere);
        Id=(EditText)findViewById(R.id.EditTextId);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.btnRGroup);
        rMale =findViewById(R.id.btnMale);
        rFemale =findViewById(R.id.btnFemale);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail=email.getText().toString();
                strPass=password.getText().toString();
                strName=name.getText().toString();
                strMobile=mobile.getText().toString();
                strId=Id.getText().toString();

                if(rMale.isChecked())
                    strSex="male";
                else if(rFemale.isChecked())
                    strSex="female";
                else
                    strSex="";

                if (strEmail.isEmpty()) {
                    email.setError("Email");
                    email.requestFocus();
                }
                else if (strPass.isEmpty()){
                    password.setError("Password");
                    password.requestFocus();
                }
                else if(strName.isEmpty()){
                    name.setError("Name");
                    name.requestFocus();
                }
                else if(strMobile.isEmpty()){
                    mobile.setError("Mobile");
                    mobile.requestFocus();
                }
                else if(!(strEmail.isEmpty() && strPass.isEmpty() && strMobile.isEmpty() && strName.isEmpty())){
                    new Register.RegisterUser(strName, strEmail, strMobile, strId, strPass,strSex).execute();

                }
                else
                    Toast.makeText(Register.this,"Error found, Please try later", Toast.LENGTH_LONG).show();
            }
        });


    }

    private class RegisterUser extends AsyncTask<Void,Void,Void> {
        String aName,aEmail,aMobile,aId,aPassword,aSex;

        public RegisterUser(String aName, String aEmail, String aMobile, String aId, String aPassword, String aSex) {
            this.aName = aName;
            this.aEmail = aEmail;
            this.aMobile = aMobile;
            this.aId = aId;
            this.aPassword = aPassword;
            this.aSex = aSex;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;

            try {
                //http://localhost:8080/Lendit/lendit/user/register&1895268&sony&sonym3@gmail.com&abcd&9400555121&male
                url = new URL("http://"+ipAddress+":8080/Lendit/lendit/user/register&"+aId+"&"+aName+"&"+aEmail+"&"
                        +aPassword+"&"+aMobile+"&"+aSex);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();


                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);
                InputStreamReader myInput= new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject obj =new JSONObject(response.toString());
                userStatus=""+obj.getString("Status");
                message=""+obj.getString("Message");


            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if(userStatus.equals("ok")) {
                Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Register.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
            else
                Toast.makeText(Register.this,"Registration failed",Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);

        }
    }
}
