package com.abc.apps.projectdraft;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;

import java.util.ArrayList;


public class orderAdapter extends ArrayAdapter<order> {
    private int resourceLayout;
    private Context mContext;
        public orderAdapter(Context context, int resource, ArrayList<order> users) {
            super(context, resource, users);
            this.resourceLayout=resource;
            this.mContext=context;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            order user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(mContext);
                convertView=vi.inflate(resourceLayout,null);


            }
            // Lookup view for data population
            TextView num = (TextView) convertView.findViewById(R.id.ordernum);
            TextView userN = (TextView) convertView.findViewById(R.id.User);
            // Populate the data into the template view using the data object
            num.setText(user.getOrderID()+"");
           userN.setText(user.getCustomerId());
            // Return the completed view to render on screen
            return convertView;
        }
    }

