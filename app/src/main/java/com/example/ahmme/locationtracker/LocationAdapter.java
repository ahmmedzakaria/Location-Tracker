package com.example.ahmme.locationtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Laptop Dream on 07-Jul-16.
 */
public class LocationAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<LocationTracker> contactList;
    ViewHolder viewHolder;
    LocationTracker tracker =new LocationTracker();
    public LocationAdapter(Context context, ArrayList<LocationTracker> contactList) {
        super(context, R.layout.row_layout,contactList);
        this.context=context;
        this.contactList=contactList;
    }
    public class ViewHolder{
        TextView sirialNoTV;
        TextView locationTV;
        TextView timeTV;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.sirialNoTV = (TextView) convertView.findViewById(R.id.sirialNo);
            viewHolder.locationTV = (TextView) convertView.findViewById(R.id.locationR);
            viewHolder.timeTV = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);
        }
        else {
            convertView.getTag();
        }

        try{
            viewHolder.sirialNoTV.setText(String.valueOf(contactList.get(position).getId()));
            viewHolder.locationTV.setText(contactList.get(position).getAddress());
            viewHolder.timeTV.setText(contactList.get(position).getTime());
        }catch (Exception e){
            Toast.makeText(context, "collecting info", Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }
}
