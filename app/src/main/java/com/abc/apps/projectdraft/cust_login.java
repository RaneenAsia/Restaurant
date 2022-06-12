package com.abc.apps.projectdraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class cust_login extends AppCompatActivity {
private EditText logintxt;
private EditText passwordtxt;
private CheckBox check;
public static int size1=0;
public static int size2=0;
SharedPreferences sharedpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_login);
        logintxt=findViewById(R.id.edtName);
        passwordtxt=findViewById(R.id.edtPL);
        check=findViewById(R.id.check);
        sharedpref=this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void btnLogin_onClick(View view) {
        String login=logintxt.getText().toString();
        String password=passwordtxt.getText().toString();
        if(login.isEmpty()||password.isEmpty()){
            Toast.makeText(cust_login.this,"empty parameters",Toast.LENGTH_SHORT).show();
        }else {
            Thread thread=new Thread(new checkCust(login,password));
            thread.start();
        }
    }

    public void remember_onClick(View view) {

        if(check.isChecked()){
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString(getString(R.string.username_key),logintxt.getText().toString());
            editor.putString(getString(R.string.password_key),passwordtxt.getText().toString());
            editor.apply();
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
            RequestQueue queue = Volley.newRequestQueue(cust_login.this);
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

                    Toast.makeText(cust_login.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);
            if (size2 != 0) {
                Log.e("hi","Done");
            } else {
                Looper.prepare();
                Toast.makeText(cust_login.this, "Ughhh",
                        Toast.LENGTH_SHORT).show();
            }
        };


    }

    public void btnSign_OnClick(View view) {
        Intent intent=new Intent(this, signup_main.class);
        startActivity(intent);

    }

//    public void btnContinue_OnClick(View view) {
//    Thread thread2=new Thread(new checkamt());
//        thread2.start();
//    }
//    class checkamt implements Runnable {
//
//        public checkamt(){
//
//        }
//        @Override
//        public void run(){
//            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//            String url = "http://172.19.13.45/rest/cust_amt.php";
//            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
//                    null, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray response) {
//                    ArrayList<String> users = new ArrayList<>();
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject obj = response.getJSONObject(i);
//                            users.add(obj.getString("username"));
//                        } catch (JSONException exception) {
//                            Log.d("Error", exception.toString());
//                        }
//                    }
//                    size1 = users.size();
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(MainActivity.this, error.toString(),
//                            Toast.LENGTH_SHORT).show();
//                    Log.d("Error_json", error.toString());
//                }
//            });
//
//            queue.add(request);
//
//            if (size1 != 0) {
//                Log.e("hi","Done");
//                addCust("Guest"+(size1+1),"temp");
//            } else {
//                Looper.prepare();
//                Toast.makeText(MainActivity.this, "Ughhh",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//
//
//    }
//    private void addCust(String user,String pass) {
//
//            String url1 = "http://172.19.13.45/rest/add_user.php";
//            RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
//            StringRequest request1 = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.e("TAG", "RESPONSE IS " + response);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        // on below line we are displaying a success toast message.
//                        Toast.makeText(MainActivity.this,
//                                jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new com.android.volley.Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // method to handle errors.
//                    Toast.makeText(MainActivity.this,
//                            "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    // as we are passing data in the form of url encoded
//                    // so we are passing the content type below
//                    return "application/x-www-form-urlencoded; charset=UTF-8";
//                }
//
//                @Override
//                protected Map<String, String> getParams() {
//
//                    // below line we are creating a map for storing
//                    // our values in key and value pair.
//                    Map<String, String> params = new HashMap<String, String>();
//
//                    // on below line we are passing our
//                    // key and value pair to our parameters.
//                    params.put("username", user);
//                    params.put("passwrd", pass);
//                    params.put("payments", "0");
//                    // at last we are returning our params.
//                    return params;
//                }
//            };
//            // below line is to make
//            // a json object request.
//            queue1.add(request1);
//        }

}