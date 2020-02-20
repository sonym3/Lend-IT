package com.example.lendit.com.example.lendit.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lendit.R;

import java.util.ArrayList;

public class ItemsListAdapter extends BaseAdapter {

     ArrayList<String> itemId;
     ArrayList<String> itemName;
     ArrayList<String> itemDesc;
      ArrayList<String> isAvail;
      ArrayList<String> availableDate;

     LayoutInflater inflter;

    public ItemsListAdapter(Context context, ArrayList<String> itemId, ArrayList<String> itemName,
                            ArrayList<String> itemDesc, ArrayList<String> isAvail,
                            ArrayList<String> availableDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.isAvail = isAvail;
        this.availableDate = availableDate;
        inflter = LayoutInflater.from(context);    }

    @Override
    public int getCount() {
        return itemId.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.item_list_adapter_layout, null);
        TextView id = (TextView) view.findViewById(R.id.itemId);
        TextView name = (TextView) view.findViewById(R.id.itemName);
        TextView desc = (TextView) view.findViewById(R.id.itemDesc);
        TextView isavail = (TextView) view.findViewById(R.id.isAvail);
        TextView availDate = (TextView) view.findViewById(R.id.availableDate);





        id.setText(itemId.get(i));
        name.setText(itemName.get(i));
        desc.setText(itemDesc.get(i));
        isavail.setText(isAvail.get(i));
        availDate.setText(availableDate.get(i));



        return view;
    }
}
