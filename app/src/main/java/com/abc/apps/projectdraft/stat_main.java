package com.abc.apps.projectdraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class stat_main extends AppCompatActivity {
    ListView lst;
    String str;
    RequestQueue queue;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_page);
        lst=findViewById(R.id.status);
        Intent intent=getIntent();
        str=intent.getStringExtra("username");
        Thread thread=new Thread(new Orders());
         thread.start();
    }
    class Orders implements Runnable{
        String url="http://192.168.1.104/rest/cust_order.php?customerID="+str;
        public Orders(){}
        @Override
        public void run() {
            queue= Volley.newRequestQueue(stat_main.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> orders = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            orders.add(obj.getString("orderID")+"-"+obj.getString("status"));
                            Log.v("hi", obj.toString() );
                        }catch(JSONException exception){
                            Log.d("Error", exception.toString());
                        }
                    }


                    ArrayAdapter adapter = new ArrayAdapter(stat_main.this,android.R.layout.simple_list_item_1,orders);
                    lst.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(stat_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });


            queue.add(request);

        }
    }

}
