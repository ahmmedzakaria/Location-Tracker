package com.example.ahmme.locationtracker;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TrackingService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Geocoder geocoder;
    List<Address> locationList;
    LocationTracker tracker;
    LocationManager manager;
    final static String MY_ACTION = "MY_ACTION";
    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        tracker = new LocationTracker();
        manager = new LocationManager(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        manager.deleteAllLocationInfo();


        Toast.makeText(TrackingService.this, "Service running", Toast.LENGTH_SHORT).show();


        MyThread myThread = new MyThread();
        myThread.start();
        return START_REDELIVER_INTENT;

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


    }


    @Override
    public void onLocationChanged(Location location) {

        geocoder=new Geocoder(this);
        try {
            locationList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),10);

            tracker.setAddress(locationList.get(0).getAddressLine(0));
            tracker.setCity(locationList.get(0).getAddressLine(1));
            tracker.setCountry(locationList.get(0).getAddressLine(2));
        } catch (IOException e) {
            e.printStackTrace();
        }


        tracker.setLongitude(String.valueOf(location.getLongitude()));
        tracker.setLatitude(String.valueOf(location.getLatitude()));
        Calendar calendar = Calendar.getInstance();
        tracker.setTime(String.valueOf(dateFormat.format(calendar.getTime())));
//        Toast.makeText(TrackingService.this, String.valueOf(dateFormat.format(calendar.getTime())), Toast.LENGTH_SHORT).show();

        LocationTracker locationInfo=new LocationTracker(tracker.getLatitude(), tracker.getLongitude(), tracker.getTime(), tracker.getAddress()+", "+tracker.getCity());

        manager.addLocation(locationInfo);
        LocationTracker lastLoation=manager.getLastLocatin();


        Intent intent = new Intent();
        intent.setAction(MY_ACTION);

        intent.putExtra("Location",lastLoation.getAddress());

        sendBroadcast(intent);


    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(TrackingService.this, "Service Stoped", Toast.LENGTH_SHORT).show();
        googleApiClient.disconnect();
        super.onDestroy();
    }

    public class MyThread extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(10000);
                    Intent intent = new Intent();
                    intent.setAction(MY_ACTION);
                    sendBroadcast(intent);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
