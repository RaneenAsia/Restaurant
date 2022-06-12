package com.abc.apps.projectdraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    List<Item> itemList;
    Context context;
    List<Order> orderList;

    public ItemAdapter(Context context, List<Item> itemList, List<Order> orderList){
        this.context = context;
        this.itemList = itemList;
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String catName = itemList.get(position).getCatName();
        String itemName = itemList.get(position).getItemName();
        String description = itemList.get(position).getDescription();
        Double price = itemList.get(position).getPrice();
        ImageButton plusBtn;

//        ImageButton plusBtn = itemList.get(position).get

        holder.itemName.setText(itemName);
        holder.description.setText(description);
        holder.priceOfItem.setText(""+price);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView description, itemName, priceOfItem, amount;
        ImageButton plusBtn, minusBtn;
        Button goToCart, newAddBtn;
        int quantity = 0;

        public ViewHolder(View itemView){
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            description = itemView.findViewById(R.id.description);
            priceOfItem = itemView.findViewById(R.id.priceOfItem);
           plusBtn = itemView.findViewById(R.id.plusBtn);
           minusBtn = itemView.findViewById(R.id.minusBtn);
           amount = itemView.findViewById(R.id.amount);
           goToCart =itemView.findViewById(R.id.goToCart);
           newAddBtn = itemView.findViewById(R.id.newAddBtn);


//            plusBtn.setOnClickListener();
            itemView.setOnClickListener(this);


            goToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  Intent intent = new Intent(context, OrderScreen.class);
//                    Bundle args = new Bundle();
//                    args.putSerializable("ARRAYLIST",(Serializable) orderList);
//                    intent.putExtra("BUNDLE",args);
//                    context.startActivity(intent);

                    Bundle args = new Bundle();

                        args.putSerializable("ARRAYLIST", (Serializable) orderList);
                        intent.putExtra("BUNDLE", args);
                        context.startActivity(intent);
                }
            });

//            newAddBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String name = (String) itemName.getText();
//                    if(quantity>0){
//                        for(int i = 0; i<itemList.size(); i++){
//                            if(itemList.get(i).getItemName() == name){
//                                Order order = new Order();
//                                order.setQuantity(quantity);
//                                order.setItem(itemList.get(i));
//                                orderList.add(order);
//                            }
//                        }
//                    }
//                }
//            });
            newAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView iteName = (TextView)itemView.findViewById(R.id.itemName);
                    String name = (String) iteName.getText();
                    if(quantity >0){
                        for(int i = 0; i<itemList.size(); i++){
                            if(itemList.get(i).getItemName() == name){
                                Order order = new Order(quantity, itemList.get(i));
                                orderList.add(order);
//                                Intent intent = new Intent(context, OrderScreen.class);
//                                intent.putExtra("order",order);
//                                context.startActivity(intent);
                            }
                        }

                    }
                }
            });

            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(quantity !=0){
                        quantity --;
                        TextView a = (TextView)itemView.findViewById(R.id.amount);
                        TextView pr = (TextView) itemView.findViewById(R.id.priceOfItem);
                        a.setText(Integer.toString(quantity));
                        Double basePrice = Double.parseDouble((String) pr.getText());
                        if(quantity>0){
                            Double newPrice = (basePrice * quantity )/(quantity + 1);
                            pr.setText(Double.toString(newPrice));
                        }
                    }
                }
            });
            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quantity++;
                    TextView a = (TextView)itemView.findViewById(R.id.amount);
                    TextView pr = (TextView)itemView.findViewById(R.id.priceOfItem);
                    a.setText(Integer.toString(quantity));
                    Double basePrice = Double.parseDouble((String) pr.getText());
                    if(quantity>1){
                        Double newPrice = (basePrice * quantity )/(quantity -1);
                        pr.setText(Double.toString(newPrice));
                    }

                }
            });
        }

        @Override
        public void onClick(View view) {

        }

    }


}
