package com.example.lendit.com.example.lendit.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.lendit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ItemsList extends AppCompatActivity {
    private String ipAddress = "192.168.1.182";
    private ProgressDialog progressDialog;
    private ListView itemList;
    private JSONArray itemListArray = new JSONArray();
    private JSONObject itemListObject = new JSONObject();
    private String userStatus;
    private SearchView itemSearchView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        itemSearchView2 = (SearchView) findViewById(R.id.simpleSearchView2);


        progressDialog = new ProgressDialog(this);

        new getItemList().execute();

        itemSearchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    new ItemsList.getItemList().execute();                }
                else
                    callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                new ItemsList.getItemBySearch().execute();
            }

        });

    }

    private class getItemList extends AsyncTask<Void, Void, Void> {

        String userStatus;


        @Override
        protected void onPreExecute() {


            progressDialog.setMessage("Loading...");
            progressDialog.show();

            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;

            try {

                url = new URL("http://" + ipAddress + ":8080/Lendit/lendit/item/getItems");

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();


                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());
                userStatus = "" + obj.getString("Status");
                itemListArray = obj.getJSONArray("itemLists");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            if(userStatus.equals("Ok")){
                try {
                    System.out.println(itemListArray.toString());
                    getRecyclerData(itemListArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
            }
            else{
                Toast.makeText(ItemsList.this,"Unable to fetch",Toast.LENGTH_LONG).show();
                progressDialog.hide();

            }
            super.onPostExecute(result);
        }
    }

    private void getRecyclerData(final JSONArray mainArray) throws JSONException {
        final ArrayList<String> itemID = new ArrayList<String>(mainArray.length());
        final ArrayList<String> itemName = new ArrayList<String>(mainArray.length());
        final ArrayList<String> itemDesc = new ArrayList<String>(mainArray.length());
        final ArrayList<String> isAvail = new ArrayList<String>(mainArray.length());
        final ArrayList<String> AvailDate = new ArrayList<String>(mainArray.length());

        for(int j=0;j<mainArray.length();j++)
        {
            JSONObject a;
            a=mainArray.getJSONObject(j);
            itemID.add(a.getString("ItemId"));
            itemName.add(a.getString("ItemName"));
            itemDesc.add(a.getString("ItemDesc"));
            String temp=a.getString("isAvailable");
            if(temp.equals("1")){
                isAvail.add("Available");
            }
            else
                isAvail.add("Not Available");

            AvailDate.add(a.getString("AvailableDate"));

        }

        itemList = (ListView)findViewById(R.id.simpleListView2);
        final ItemsListAdapter itemsListAdapter=new ItemsListAdapter(ItemsList.this,itemID,itemName,itemDesc,isAvail,AvailDate);
        itemList.setAdapter(itemsListAdapter);

        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_position=position;
                new AlertDialog.Builder(ItemsList.this).setIcon(R.drawable.ic_delete).
                        setTitle("Are you sure?").setMessage("Do you want to delete Item").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String number=""+itemID.get(which_position);
                                new ItemsList.itemDelete(number).execute();
                                itemID.remove(which_position);
                                itemName.remove(which_position);
                                itemDesc.remove(which_position);
                                isAvail.remove(which_position);
                                AvailDate.remove(which_position);
                                itemsListAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No",null).show();
                return true;
            }
        });

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_position=position;
                Intent i = new Intent(ItemsList.this,ItemSingleAdmin.class);
                try {
                    System.out.println("$%^&*$%^&%^&   "+mainArray.getJSONObject(which_position).toString());
                    i.putExtra("jsonObject",mainArray.getJSONObject(which_position).toString());
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private class itemDelete extends AsyncTask<Void, Void, Void> {

        String itemToBeDeleted;
        public itemDelete(String itemTobeDeleted) {
            this.itemToBeDeleted = itemTobeDeleted;
        }

        @Override
        protected void onPreExecute() {
            System.out.println(itemToBeDeleted);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;

            try {


                url = new URL("http://" + ipAddress + ":8080/Lendit/lendit/item/deleteItem&"+itemToBeDeleted);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();


                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

                JSONObject obj = new JSONObject(response.toString());
                userStatus = "" + obj.getString("Status");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            progressDialog.hide();

            if(userStatus.equals("Ok")) {
                Toast.makeText(ItemsList.this,"Deletion Successful",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ItemsList.this,"Deletion failed",Toast.LENGTH_SHORT).show();

            super.onPostExecute(result);
        }
    }


    private class getItemBySearch extends AsyncTask<Void, Void, Void> {
        String searchKey=itemSearchView2.getQuery().toString();

        @Override
        protected void onPreExecute() {


            progressDialog.setMessage("Loading...");
            progressDialog.show();

            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;

            try {

                url = new URL("http://" + ipAddress + ":8080/Lendit/lendit/item/search&"+searchKey);

                HttpURLConnection client = null;

                client = (HttpURLConnection) url.openConnection();

                client.setRequestMethod("GET");

                int responseCode = client.getResponseCode();


                System.out.println("\n Sending 'GET' request to URL : " + url);

                System.out.println("Response Code : " + responseCode);
                InputStreamReader myInput = new InputStreamReader(client.getInputStream());

                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());
                JSONObject obj = new JSONObject(response.toString());
                userStatus = "" + obj.getString("Status");
                itemListArray = obj.getJSONArray("itemLists");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            if(userStatus.equals("Ok")) {
                try {
                    getRecyclerData(itemListArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
            }
            else
                Toast.makeText(ItemsList.this,"Unable to fetch",Toast.LENGTH_LONG).show();
            super.onPostExecute(result);

        }
    }
}
