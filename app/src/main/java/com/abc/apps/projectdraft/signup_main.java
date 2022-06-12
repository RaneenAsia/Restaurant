package com.abc.apps.projectdraft;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signup_main extends AppCompatActivity {
private EditText username;
private EditText password;
private EditText pass_conf;
public static int size=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        username=findViewById(R.id.username);
        password=findViewById(R.id.edtPL);
        pass_conf=findViewById(R.id.conPL);
    }

    public void btnSubmit_OnClick(View view) {
        String user=username.getText().toString();
        String pass=password.getText().toString();
        String passConf=pass_conf.getText().toString();
        if(pass.isEmpty()||user.isEmpty()||passConf.isEmpty()){
            Toast.makeText(signup_main.this, "empty fields",Toast.LENGTH_SHORT).show();

        }
        else if(!pass.equals(passConf)){
            Toast.makeText(signup_main.this, "unmatching passwords",Toast.LENGTH_SHORT).show();

        }
        else{

            Thread thread3=new Thread(new addCust(user,pass));
            thread3.start();
        }
    }
    class addCust implements Runnable {
        String user;
        String pass;

        addCust(String user, String pass) {
            this.pass = pass;
            this.user = user;
        }
         Toast toast=Toast.makeText(signup_main.this,"username already exists",Toast.LENGTH_SHORT);

        @Override
        public void run() {
            RequestQueue queue = Volley.newRequestQueue(signup_main.this);
            String url = "http://172.19.13.45/rest/check_user.php?username="+user;
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
                    size = names.size();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(signup_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);
            if (size != 0) {

               toast.show();
                Log.e("hi", "Done");
            } else {
                String url1 = "http://172.19.13.45/rest/add_user.php";
                RequestQueue queue1 = Volley.newRequestQueue(signup_main.this);
                StringRequest request1 = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "RESPONSE IS " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            // on below line we are displaying a success toast message.
                            Toast.makeText(signup_main.this,
                                    jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // method to handle errors.
                        Toast.makeText(signup_main.this,
                                "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        // as we are passing data in the form of url encoded
                        // so we are passing the content type below
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() {

                        // below line we are creating a map for storing
                        // our values in key and value pair.
                        Map<String, String> params = new HashMap<String, String>();

                        // on below line we are passing our
                        // key and value pair to our parameters.
                        params.put("username", user);
                        params.put("passwrd", pass);
                        params.put("payments", "0");
                        // at last we are returning our params.
                        return params;
                    }
                };
                // below line is to make
                // a json object request.
                queue1.add(request1);
                Intent intent=new Intent(signup_main.this,cust_login.class);
            }
        }
    }

}
