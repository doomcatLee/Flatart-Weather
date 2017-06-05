package com.example.guest.weatherandroid;

public class Constants {

    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    public static final String YOUR_QUERY_PARAMETER = "zip"; //Example: "location"
    public static final String API_KEY_QUERY_PARAMETER = "appid";

    public static final String FIREBASE_CHILD_SEARCHED_LOCATION = "searchedLocation";
}