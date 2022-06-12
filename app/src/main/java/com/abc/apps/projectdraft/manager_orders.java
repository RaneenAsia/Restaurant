package com.abc.apps.projectdraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class  manager_orders extends AppCompatActivity {
    private TextView name;
    private TextView ordertxt;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        name=findViewById(R.id.usen);
        ordertxt=findViewById(R.id.det);
        Intent intent=getIntent();
        order_class msg=(order_class)intent.getSerializableExtra("KEY_NAME");

        id=msg.getOrderID();
        name.setText(msg.getOrderID()+"-"+msg.getCustomerId());
        String[] txt=msg.getOrder().split(" : ");
        String fin="Order:\n";
        for(int i=0;i<txt.length;i++) {
            fin+=txt[i]+"\n";
        }
        fin+="Address:\n"+msg.getAddress()+"\nPayment: \n"+msg.getPayment();
        ordertxt.setText(fin);
    }


    public void don_onClick(View view) {
        String url = "http://192.168.1.104/rest/deleteOrder.php";
        RequestQueue queue = Volley.newRequestQueue(manager_orders.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(manager_orders.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(manager_orders.this,
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
                Map<String, String> params = new HashMap<>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("orderID", id+"");

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Intent intent= new Intent(manager_orders.this,manager_main.class);
        startActivity(intent);
    }

}

