package com.example.exam_tasker_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    MyLInk JSON_URl = new MyLInk();
    String URL = JSON_URl.getJSON_URL();



    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.Subject_LV);
        loadJSONFromURL(URL);
    }

    //cделать гибче
    private void loadJSONFromURL(String url){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.Subject_PB);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
        new Response.Listener<String>(){
            public void onResponse(String response){
                progressBar.setVisibility(View.INVISIBLE);
                try{
                    JSONObject object = new JSONObject(EncodingToUTF8(response));
                    //subject --- внутри джейсона коорый в пуtи ссылки
                    JSONArray jsonArray = object.getJSONArray("subject");
                    ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                    ListAdapter adapter = new ListViewAdapter(getApplicationContext(),R.layout.adp_js, R.id.TV_NAME,listItems);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String tmp = (String) listView.getItemAtPosition(position).toString().substring(8);
                int length = tmp.length();
                tmp=tmp.substring(1,length-2);

                MyLInk.pos = URL.lastIndexOf("/")+1;
                String tmpURL = URL.substring(0,MyLInk.pos) + tmp + "/" + tmp;
                MyLInk.pos=MyLInk.pos+tmp.length();
                URL= tmpURL;
                loadJSONFromURL(tmpURL); //тут все сделанно только надо гит репозиторий делать по аналогии с inscribed circle and triangle фишка в двойном повторении нужного пути

            }
        });
    }



    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList<JSONObject> aList = new ArrayList<JSONObject>();
        try{
            if(jsonArray!=null){
                for(int i =0; i<jsonArray.length();i++){
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(aList);
        return aList;
    }
    public static String EncodingToUTF8(String responce){
        try{
            byte[] code = responce.toString().getBytes("UTF-8");
            responce = new String(code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return responce;
    }
}