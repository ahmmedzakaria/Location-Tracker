package com.example.ahmme.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Laptop Dream on 14-Jul-16.
 */
public class LocationManager {
    private DataBaseHelper helper;
    private SQLiteDatabase database;
    LocationTracker traker =new LocationTracker();
    private Context context;

    public LocationManager(Context context) {
        this.context = context;
        helper = new DataBaseHelper(context);

    }

    private void open() {
        database = helper.getWritableDatabase();

    }

    private void close() {
        helper.close();
    }

    public boolean addLocation(LocationTracker traker) {

        this.open();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.COL_LACTITUDE, traker.getLatitude());
        contentValues.put(DataBaseHelper.COL_LONGITUDE, traker.getLongitude());
        contentValues.put(DataBaseHelper.COL_TIME, traker.getTime());
        contentValues.put(DataBaseHelper.COL_ADDRESS, traker.getAddress());

        long inserted = database.insert(DataBaseHelper.TABLE_LOCATION_INFO, null, contentValues);
        this.close();

        database.close();

        if (inserted > 0) {
            return true;
        } else return false;

    }

    public ArrayList<LocationTracker> getAllLoationInfo() {

        this.open();

        ArrayList<LocationTracker> contactList = new ArrayList<>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_LOCATION_INFO, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String lactitude = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_LACTITUDE));
                String longitude = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_LONGITUDE));
                String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TIME));
                String address = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_ADDRESS));

                LocationTracker trackerinfo = new LocationTracker(id, lactitude, longitude, time, address);
                contactList.add(trackerinfo);
                cursor.moveToNext();
            }
            this.close();

        }
        return contactList;
    }

    public ArrayList<LocationTracker> getAllTimeAndAddress() {

        this.open();

        ArrayList<LocationTracker> contactList = new ArrayList<>();

        Cursor cursor = database.query(DataBaseHelper.TABLE_LOCATION_INFO, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
                String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TIME));
                String address = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_ADDRESS));

                LocationTracker trackerinfo = new LocationTracker(id,time, address);
                contactList.add(trackerinfo);
                cursor.moveToNext();
            }
            this.close();

        }
        return contactList;
    }


    public LocationTracker getLastLocatin() {

        this.open();

        Cursor cursor = database.query(DataBaseHelper.TABLE_LOCATION_INFO, new String[]{DataBaseHelper.COL_ID,
                        DataBaseHelper.COL_LACTITUDE, DataBaseHelper.COL_LONGITUDE, DataBaseHelper.COL_TIME, DataBaseHelper.COL_ADDRESS},
                null, null, null, null, null);
        cursor.moveToLast();

        int columId = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COL_ID));
        String lactitude = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_LACTITUDE));
        String longititude = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_LONGITUDE));
        String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_TIME));

        String address = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COL_ADDRESS));


        LocationTracker contact = new LocationTracker(columId, lactitude, longititude, time, address);
        this.close();
        return contact;
    }



    public boolean deleteAllLocationInfo() {
        this.open();
        int deleted = database.delete(DataBaseHelper.TABLE_LOCATION_INFO,null,null);
        this.close();
        if (deleted > 0) {
            return true;
        } else return false;
    }


}
