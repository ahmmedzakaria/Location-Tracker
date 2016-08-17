package com.example.ahmme.locationtracker;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView locationText;
    ListView listViewLV;
    LocationTracker tracker;
    LocationManager manager;
    MyReceiver myReceiver;
    private String datapassed;
    Switch mySwitch = null;
    LocationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationText=(TextView) findViewById(R.id.loation);
        listViewLV=(ListView)findViewById(R.id.listView);

        mySwitch=(Switch)findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mySwitch.isChecked()){
                    mySwitch.setText("Tracking ON");
                    Intent intent = new Intent(MainActivity.this,com.example.ahmme.locationtracker.TrackingService.class);
                    startService(intent);

                }else if(!mySwitch.isChecked()){
                    mySwitch.setText("Tracking OFF");
                    stopService(new Intent(MainActivity.this,TrackingService.class));

                }
            }
        });

        tracker=new LocationTracker();
        manager=new LocationManager(this);




//        locationText.setText(datapassed);

    }







    @Override
    protected void onStart() {
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TrackingService.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service


        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            datapassed = arg1.getStringExtra("Location");
            locationText.setText(datapassed);
            try{
                LocationTracker lastLoation=manager.getLastLocatin();
                if(datapassed==null){
                    locationText.setText(lastLoation.getAddress());
                }else {
                    locationText.setText(datapassed);

                }
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Collecting Location...", Toast.LENGTH_SHORT).show();
            }
            showListView();



        }


    }


    public void showMap(View view) {
        Intent intent=new Intent(MainActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    private void showListView(){
        ArrayList<LocationTracker> allLocation=manager.getAllTimeAndAddress();
        adapter=new LocationAdapter(this,allLocation);
        listViewLV.setAdapter(adapter);

    }

}
