package com.example.ahmme.locationtracker;

/**
 * Created by ahmme on 14-08-16.
 */
public class LocationTracker {
    private int id;
    private String latitude;
    private String longitude;
    private String time;
    private String address;
    private String city;
    private String country;

    public LocationTracker(String latitude, String longitude, String time, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time=time;
        this.address = address;
    }

    public LocationTracker(int id, String latitude, String longitude, String time, String address) {

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time=time;
        this.address = address;
    }

    public LocationTracker(int id, String time, String address) {
        this.id = id;
        this.time=time;
        this.address = address;
    }

    public LocationTracker() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
