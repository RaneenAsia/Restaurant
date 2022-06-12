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

public class deliver_main extends AppCompatActivity {
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);
        Intent intent=getIntent();
        Thread thread =new Thread(new task());
        thread.start();
    }
    class task implements Runnable{
        ListView lst=findViewById(R.id.deliverLst);
        String url="http://192.168.1.104/rest/deliver_json.php";
        public task(){
        }
        @Override
        public void run(){
            queue= Volley.newRequestQueue(deliver_main.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ArrayList<order_class> orders = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            orders.add(new order_class(Integer.parseInt(obj.getString("orderID")), obj.getString("customerID"), obj.getString("orderCon"),obj.getString("status"),Double.parseDouble(obj.getString("payment")),obj.getString("Address")));
                            Log.e("hi", obj.toString() );
                        }catch(JSONException exception){
                            Log.d("Error", exception.toString());
                        }
                    }
//                    String[] arr = new String[orders.size()];
//                    arr = orders.toArray(arr);
//                    ArrayList<order> ordersF=new ArrayList<>();
//                    for(int i=0;i<orders.size();i++){
//                        String[] del=arr[i].split(",");
//                        for(int j=0;j<del.length;j++){
//                            ordersF.add(new order(Integer.parseInt(del[0]),del[1],del[2],del[3],Double.parseDouble(del[4]),del[5]));
//                        }
//                    }
                    orderAdapter adapter = new orderAdapter(deliver_main.this, R.layout.order_view,orders);
// Attach the adapter to a ListView
//                    ListView listView = (ListView) findViewById(R.id.lvItems);
//                    listView.setAdapter(adapter);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                            deliver_main.this, android.R.layout.simple_list_item_1,
//                            orders);
                    lst.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(deliver_main.this, error.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("Error_json", error.toString());
                }
            });


            queue.add(request);
//            Toast.makeText(chef_main.this, "added",
//                    Toast.LENGTH_SHORT).show();
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    order_class listItem =(order_class) lst.getItemAtPosition(position);

                    String[] str=listItem.toString().split(",");
                    order_class ord1=new order_class(Integer.parseInt(str[0]),str[1],str[2],str[3],Double.parseDouble(str[4]),str[5]);
                    Intent intent=new Intent(deliver_main.this, deliverOrders.class);
                    intent.putExtra("KEY_NAME",ord1);
                    startActivity(intent);

                }
            });
        }

    }


}



