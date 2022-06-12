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

public class employeeLogin extends AppCompatActivity {
    private EditText logintxt;
    private EditText passwordtxt;
    private CheckBox check;
    public static int size1=0;
    public static int size2=0;
    String cat;
    SharedPreferences sharedpref;
    Intent destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        logintxt=findViewById(R.id.nameTxt);
        passwordtxt=findViewById(R.id.edtPass);
        check=findViewById(R.id.checkEmp);
        sharedpref=this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void rememberEmp_onClick(View view) {
        if(check.isChecked()){
            SharedPreferences.Editor editor=sharedpref.edit();
            editor.putString(getString(R.string.usernameEmp_key),logintxt.getText().toString());
            editor.putString(getString(R.string.passwordEmp_key),passwordtxt.getText().toString());
            editor.apply();
        }
    }

    public void btnLoginEmp_onClick(View view) {
        String login=logintxt.getText().toString();
        String password=passwordtxt.getText().toString();
        if(login.isEmpty()||password.isEmpty()){
            Toast.makeText(employeeLogin.this,"empty parameters",Toast.LENGTH_SHORT).show();
        }else {
            Thread thread=new Thread(new checkEmp(login,password));
            thread.start();
        }
    }
    class checkEmp implements Runnable {
        String log;
        String pass;
        public checkEmp(String log,String pass){
            this.log=log;
            this.pass=pass;
        }
        @Override
        public void run(){
            RequestQueue queue = Volley.newRequestQueue(employeeLogin.this);
            String url = "http://192.168.1.104/rest/login_emp.php?username=" + log + "&passwrd=" + pass;
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
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
                    size2 = names.size();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(employeeLogin.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });

            queue.add(request);
            if (size2 != 0) {
                if(cat.equals("Chef")) {
                    destination=new Intent(employeeLogin.this, chef_main.class);

                }else if(cat.equals("Deliver")) {
                    destination=new Intent(employeeLogin.this, deliver_main.class);

                }else if(cat.equals("Manager")) {
                    destination=new Intent(employeeLogin.this, manager_main.class);

                }
                Log.e("hi","Done");
                startActivity(destination);
            } else {
                Looper.prepare();
                Toast.makeText(employeeLogin.this, "Ughhh",
                        Toast.LENGTH_SHORT).show();
            }
        };


    }
}