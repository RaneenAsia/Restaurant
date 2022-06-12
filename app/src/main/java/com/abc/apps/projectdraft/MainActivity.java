package com.abc.apps.projectdraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private Button guestBtn;
    private Button employeeBtn;
    SharedPreferences sharedPref;
    Intent destination;
    public static int size1=0;
    public static int size2=0;
    String cat;
    String password;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        guestBtn = findViewById(R.id.guestBt);
        employeeBtn = findViewById(R.id.employeeBt);
        if(userisLoggedIn()||userEmpisLoggedIn()){
            Thread thread=new Thread(new checkCust(username,password));
            thread.start();
            if(cat.equals("cust")) {
                destination=new Intent(MainActivity.this, menuHome.class);
                destination.putExtra("username",username);

            }else if(cat.equals("Chef")) {
                destination=new Intent(MainActivity.this, chef_main.class);

            }else if(cat.equals("Deliver")) {
                destination=new Intent(MainActivity.this, deliver_main.class);

            }else if(cat.equals("Manager")) {
                destination=new Intent(MainActivity.this, manager_main.class);

            }
            startActivity(destination);

        }
    }
    public void guestBt(View view) {

        Intent intent = new Intent(this, cust_login.class);
        startActivity(intent);
    }
    public void employeeBt(View view) {
        Intent intent = new Intent(this, employeeLogin.class);
        startActivity(intent);
    }
boolean userisLoggedIn(){
if(sharedPref.getString(getString(R.string.username_key),null)!=null) {
    username = sharedPref.getString(getString(R.string.username_key), null);
    password = sharedPref.getString(getString(R.string.password_key), null);
    return username != null && password != null;
}else {
    return false;
}
}
    boolean userEmpisLoggedIn(){
    if(sharedPref.getString(getString(R.string.usernameEmp_key),null)!=null) {
        username = sharedPref.getString(getString(R.string.usernameEmp_key), null);
        password = sharedPref.getString(getString(R.string.passwordEmp_key), null);

        return username != null && password != null;
    }
    else {
        return false;
    }
    }
    class checkCust implements Runnable {
        String log;
        String pass;
        public checkCust(String log,String pass){
            this.log=log;
            this.pass=pass;
        }
        @Override
        public void run(){
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String url = "http://192.168.1.104/rest/login.php?username=" + log + "&passwrd=" + pass;
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            names.add(obj.getString("username"));
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }
                    }
                    size2 = names.size();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);
            if (size2 != 0) {
                cat="cust";

                Log.e("hi","Done");
            } else {
                RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                String url1 = "http://192.168.1.104/rest/login_emp.php?username=" + log + "&passwrd=" + pass;
                JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> names = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                names.add(obj.getString("username"));
                                cat=obj.getString("cat");
                            } catch (JSONException exception) {
                                Log.d("Error", exception.toString());
                            }
                        }
                        size1 = names.size();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, error.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("Error_json", error.toString());
                    }
                });

                queue1.add(request1);
                if (size1 != 0) {
                    Log.e("hi","Done");
                } else {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "Ughhh",
                            Toast.LENGTH_SHORT).show();
                }
            };
            }
        };


    }
