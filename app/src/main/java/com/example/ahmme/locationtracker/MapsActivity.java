package com.example.ahmme.locationtracker;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationTracker traker;
    LocationManager manager;
    Polyline line;
    private ArrayList<LatLng> points;
    ArrayList<LocationTracker> allLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        traker=new LocationTracker();
        manager=new LocationManager(this);
        points=new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        allLocationList=manager.getAllLoationInfo();
        for(LocationTracker info : allLocationList){
            LatLng latLng=new LatLng(Double.valueOf(info.getLatitude()),Double.valueOf(info.getLongitude()));
            points.add(latLng);
        }

        redrawLine();

    }

    private void redrawLine(){

        mMap.clear();  //clears all Markers and Polylines
        int count=0;
        LatLng point=new LatLng(0.0,0.0);

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            point= points.get(i);
            count+=1;
            options.add(point);
            mMap.addMarker(new MarkerOptions().position(point).title(allLocationList.get(i).getAddress()));


        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);


        Toast.makeText(MapsActivity.this,"Map total Location "+String.valueOf(count), Toast.LENGTH_SHORT).show();
        //add Marker in current position
        line = mMap.addPolyline(options); //add Polyline
    }
}
