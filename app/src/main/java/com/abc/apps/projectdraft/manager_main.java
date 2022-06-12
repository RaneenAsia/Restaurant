package com.abc.apps.projectdraft;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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


public class manager_main extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private ListView listview;
    private Button search;
    RequestQueue queue;
    RequestQueue queue1;
    RequestQueue queue2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main);
        spinner=findViewById(R.id.spin);
        listview=findViewById(R.id.viewLst);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spin, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
         String str=parent.getItemAtPosition(pos).toString();
         spinner.setOnItemSelectedListener(this);


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    public void get_onclick(View view){

    }
    class prof implements Runnable{
        String url="http://192.168.1.104/rest/profits.php";
        public prof(){

        }
        @Override
        public void run() {
            queue= Volley.newRequestQueue(manager_main.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> profits = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            profits.add(obj.toString());
                            Log.d("hi", obj.toString() );
                        }catch(JSONException exception){
                            Log.d("Error", exception.toString());
                        }
                    }
                    String[] str2=profits.get(0).split("[:}]");
                    str2[1] = str2[1].replaceAll("\"", "");
                    double profit=Double.parseDouble(str2[1])*0.2;
                    profits.set(0,profit+"");

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            manager_main.this, android.R.layout.simple_list_item_1,
                            profits);
                    listview.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(manager_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });


            queue.add(request);
//
        }
    }
    public void get_onClick(View view){
        String st=spinner.getSelectedItem().toString();
        if(st.equals("Profits")){
            Thread thread1=new Thread(new prof());
            thread1.start();
        }else if(st.equals("Comments")){
            Thread thread2=new Thread(new Comments());
                    thread2.start();
        }
        else if(st.equals("Orders")){
            Thread thread3=new Thread(new Orders());
        }
    }
    class Comments implements Runnable{
        String url="http://192.168.1.104/rest/comments.php";
        public Comments(){}
        @Override
        public void run() {
            queue1= Volley.newRequestQueue(manager_main.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> comments = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            comments.add(obj.getString("customerID")+":\n" +obj.getString("comment"));
                            Log.v("hi", obj.toString() );
                        }catch(JSONException exception){
                            Log.d("Error", exception.toString());
                        }
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            manager_main.this, android.R.layout.simple_list_item_1,
                            comments);
                    listview.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(manager_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });


            queue1.add(request);
//
        }
    }
    class Orders implements Runnable{
        String url="http://192.168.1.104/rest/orders.php";
        public Orders(){}
        @Override
        public void run() {
            queue2= Volley.newRequestQueue(manager_main.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> comments = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            comments.add(obj.getString("orderID")+"," +obj.getString("customerID")+","+ obj.getString("orderCon")+","+obj.getString("status")+","+obj.getString("payment")+","+obj.getString("Address"));
                            Log.v("hi", obj.toString() );
                        }catch(JSONException exception){
                            Log.d("Error", exception.toString());
                        }
                    }


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            manager_main.this, android.R.layout.simple_list_item_1,
                            comments);
                    listview.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(manager_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });


            queue2.add(request);
//
        }
    }

}


