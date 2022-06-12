package com.abc.apps.projectdraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class itemListPage extends AppCompatActivity {

    List<Item> itemList = new ArrayList<>();
    ArrayList<Order> orderList = new ArrayList<>();
    RecyclerView recycler;
    ImageButton plusBtn, minusBtn;
    TextView amount, priceOfItem, itemName;
    Button addToCart;

    private static  final String BASE_URL = "http://192.168.1.111/Restaurant/getItems.php?catName=Burgers";
// "http://192.168.1.111/Restaurant/getItems.php?catName=" +catName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_page);


        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        amount = findViewById(R.id.amount);
        priceOfItem = findViewById(R.id.priceOfItem);
        itemName = findViewById(R.id.itemName);
        recycler = findViewById(R.id.recyclerA);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        addToCart = findViewById(R.id.goToCart);

        loadItems();
    }

    private void loadItems(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);
                        String itemName = object.getString("itemName");
                        String description = object.getString("description");
                        Double price = object.getDouble("price");
                        String catName = object.getString("catName");
                        Item item = new Item(itemName, description, price, catName);
                        itemList.add(item);
                    }
                } catch (Exception e) {

                }
                ItemAdapter adapter = new ItemAdapter(itemListPage.this, itemList, orderList);
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(itemListPage.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(itemListPage.this).add(stringRequest);
    }
}