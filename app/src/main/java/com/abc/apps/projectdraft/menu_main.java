package com.abc.apps.projectdraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


    public class menu_main extends AppCompatActivity {

        String str;

        @Override
        protected void onCreate(Bundle savedInstanceState) {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu_options);
            Intent intent=getIntent();
           str=intent.getStringExtra("username").toString();


        }
        public void menu_onClick(View view){
            Intent destination=new Intent(menu_main.this,menuHome.class);
            destination.putExtra("username",str);

            startActivity(destination);

        }
        public void stat_onClick(View view) {
            Intent destination=new Intent(menu_main.this,stat_main.class);
            destination.putExtra("username",str);
            startActivity(destination);
        }
        public void comment_onClick(View view) {
            Intent destination=new Intent(menu_main.this, comment_main.class);
            destination.putExtra("username",str);
            startActivity(destination);
        }
    }


