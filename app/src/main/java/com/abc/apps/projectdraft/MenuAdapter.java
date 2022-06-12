package com.abc.apps.projectdraft;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.content.Intent;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private String [] captions;
    private int [] images;

    public MenuAdapter(String [] captions, int [] images){
        this.captions = captions;
        this.images = images;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,
                parent,false);
        return new ViewHolder(v);
    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//       CardView cardView = holder.cardView;
//       ImageView imageView = (ImageView) cardView.findViewById(R.id.picture);
//       Drawable dr = ContextCompat.getDrawable(cardView.getContext(), images[position]);
//       imageView.setImageDrawable(dr);
//
//       TextView txt = (TextView) cardView.findViewById(R.id.catName);
//       txt.setText(captions[position ]);

//       cardView.setOnClickListener((view -> {
////            String name = captions[position];
//
//
////           Intent intent = new Intent(, employeeLogin.class);
////           startActivity(intent);
////            Intent intent = new Intent(MenuAdapter.this, itemListPage.class);
//           Toast.makeText(view.getContext(), name, Toast.LENGTH_SHORT).show();
//       }));
//}
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.picture);
        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), images[position]);
        imageView.setImageDrawable(dr);

        TextView txt = (TextView) cardView.findViewById(R.id.catName);
        txt.setText(captions[position ]);

        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(v.getContext(), itemListPage.class);
                String clicked = captions[holder.getAbsoluteAdapterPosition()];
                intent.putExtra("category", clicked);
               // Toast.makeText(v.getContext(), captions[holder.getAbsoluteAdapterPosition()], Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

//        @Override
//        public static void onClick(View view) {
//            int position = getAdapterPosition();
//            String cat = captions[position];
//
//        }
    }
    }



