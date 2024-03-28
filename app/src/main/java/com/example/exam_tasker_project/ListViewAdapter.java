package com.example.exam_tasker_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {
    int listlayout;
    ArrayList<JSONObject> files;
    Context context;

    public ListViewAdapter(Context context, int listlayout, int field, ArrayList<JSONObject> files){
        super(context,listlayout,field,files);
        this.context=context;
        this.listlayout=listlayout;
        this.files=files;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem= inflater.inflate(listlayout, null, false);
        //tyt nado bydet sdelat'universal
        TextView name = listViewItem.findViewById(R.id.TV_NAME);
        try{
            name.setText(files.get(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listViewItem;
    }
}
