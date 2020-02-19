package com.example.lendit.com.example.lendit.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lendit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AdminAddItems extends AppCompatActivity {
    private String ipAddress="192.168.1.182";
    private EditText name;
    private EditText desc;
    private Button add;
    private RadioGroup radioGroup;
    private RadioButton available;
    private RadioButton notAvailable;
    private String userStatus;

    private String strName,strDesc,strAvail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_items);

        name=(EditText)findViewById(R.id.adminName);
        desc=(EditText)findViewById(R.id.adminDesc);
        radioGroup=(RadioGroup)findViewById(R.id.adminRadioGroup);
        available=findViewById(R.id.adminRadioAvailable);
        notAvailable=findViewById(R.id.adminRadioNotAvailable);
        add=findViewById(R.id.adminAddItem);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strName=name.getText().toString();
                strDesc=desc.getText().toString();

                if(available.isChecked())
                    strAvail="1";
                else if(notAvailable.isChecked())
                    strAvail="0";
                else
                    strAvail="";

                if (strName.isEmpty()) {
                    name.setError("Name");
                    name.requestFocus();
                }
                else if (strDesc.isEmpty()){
                    desc.setError("Description");
                    desc.requestFocus();
                }
                else if(!(strName.isEmpty() && strDesc.isEmpty() && strAvail.isEmpty())){
                    new AdminAddItems.AddItemsDatabase(strName, strDesc, strAvail).execute();

                }
                else
                    Toast.makeText(AdminAddItems.this,"Error found, Please try later", Toast.LENGTH_LONG).show();

            }
        });


    }
    private class AddItemsDatabase extends AsyncTask<Void,Void,Void> {
        String aName,aAvail,aDesc;

        public AddItemsDatabase(String aName, String aDesc, String aAvail) {
            this.aName = aName;
            this.aAvail = aAvail;
            this.aDesc = aDesc;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            URL url = null;

            try {
                //http://localhost:8080/Lendit/lendit/user/register&1895268&sony&sonym3@gmail.com&abcd&9400555121&male
                url = new URL("http://"+ipAddress+":8080/Lendit/lendit/admin/addItem&"+aName+"&"+aDesc+"&"+aAvail);

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
            if(userStatus.equals("Ok")) {
                Toast.makeText(AdminAddItems.this,"Items added Successfully",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AdminAddItems.this, AdminAddItems.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
            else
                Toast.makeText(AdminAddItems.this,"Adding failed",Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);

        }
    }


}
