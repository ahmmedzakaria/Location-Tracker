package com.example.ahmme.locationtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BITM Trainer - 401 on 7/10/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

   private static final String DATABASE_NAME = "location_tracker";
   private static final int DATABASE_VERSION = 1;

    public static final String TABLE_LOCATION_INFO = "location_info";


    public static final String COL_ID = "id";
    public static final String COL_LACTITUDE = "lactitude";
    public static final String COL_LONGITUDE= "longitude";
    public static final String COL_TIME= "time";
    public static final String COL_ADDRESS= "address";





    private static final String CREATE_LOCATION_INFO_TABLE = " CREATE TABLE " + TABLE_LOCATION_INFO +
            "( " + COL_ID + " INTEGER PRIMARY KEY," + COL_LACTITUDE + " TEXT, " + COL_LONGITUDE + " TEXT, " + COL_TIME + " TEXT, " + COL_ADDRESS + " TEXT )";



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LOCATION_INFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
