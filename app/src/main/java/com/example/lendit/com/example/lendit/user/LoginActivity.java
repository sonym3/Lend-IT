package com.example.lendit.com.example.lendit.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lendit.R;
import com.example.lendit.com.example.lendit.admin.AdminDashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    private EditText id;
    private EditText password;
    private String strEmail;
    private String strPass;
    private Button btnLogin;
    private Button btnRegister;
    private String ipAddress="192.168.1.182";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        id=(EditText)findViewById(R.id.id);
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
               strEmail=id.getText().toString();
               strPass=password.getText().toString();

               if (strEmail.isEmpty()) {
                   id.setError("ID");
                   id.requestFocus();
               }
               else if (strPass.isEmpty()){
                   password.setError("Password");
                   password.requestFocus();
               }
               else if(!(strPass.isEmpty() && strEmail.isEmpty())){
                    String userid,userPass;
                    userid=id.getText().toString();
                    userPass=password.getText().toString();
                   new LoginActivity.SignInUser(userid, userPass).execute();

               }
               else
                   Toast.makeText(LoginActivity.this,"Error found, Please try later", Toast.LENGTH_LONG).show();
           }
       });
    }


    private class SignInUser extends AsyncTask<Void,Void,Void> {
        String usrId,passwrd,userStatus,userLevel;

        public SignInUser(String id,String password) {
            usrId=id;
            passwrd=password;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;

            try {

                url = new URL("http://"+ipAddress+":8080/Lendit/lendit/credentials/validate&"+usrId+"&"+passwrd);

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
                userLevel=""+obj.getString("Level");


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

            if(userStatus.equals("OK") && userLevel.equals("user")) {
                Toast.makeText(LoginActivity.this,"Login Successful - " +userStatus,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
            else if(userStatus.equals("OK") && userLevel.equals("admin")){
                Toast.makeText(LoginActivity.this,"Login Successful - " +userStatus,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, AdminDashboard.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
            else
                Toast.makeText(LoginActivity.this,"Wrong Credentials - " +userStatus,Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
    }
}
