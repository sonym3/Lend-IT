package com.example.lendit.com.example.lendit.admin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lendit.R;

import java.util.ArrayList;

public class StudentListAdapter extends BaseAdapter {

    ArrayList<String> studentId;
    ArrayList<String> studentName;
    ArrayList<String> studentEmail;

    LayoutInflater inflter;

    public StudentListAdapter(Context appContext, ArrayList<String> studentId, ArrayList<String> studentName, ArrayList<String> studentEmail) {
        this.studentId=studentId;
        this.studentEmail=studentEmail;
        this.studentName=studentName;

        inflter = (LayoutInflater.from(appContext));

    }
    @Override
    public int getCount() {
        return studentId.size();
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

        view = inflter.inflate(R.layout.student_list_adapter_layout, null);
        TextView id = (TextView) view.findViewById(R.id.studentId);
        TextView name = (TextView) view.findViewById(R.id.studentName);
        TextView email = (TextView) view.findViewById(R.id.studentEmail);


        id.setText(studentId.get(i));
        name.setText(studentName.get(i));
        email.setText(studentEmail.get(i));



        return view;
    }
}
