package com.example.guest.weatherandroid.Model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Parcel
public class User {

    private String mHomeZipcode;
    private String mEmail;
    private String pushId;
    private String uid;

    public User(String z, String e, String i){
        mHomeZipcode = z;
        mEmail = e;
        uid = i;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    private String mCity;
    private Map<String, String> mMoodWeather = new HashMap<String, String>();


    public ArrayList<Weather> getmSavedWeather() {
        return mSavedWeather;
    }

    public void setmSavedWeather(ArrayList<Weather> mSavedWeather) {
        this.mSavedWeather = mSavedWeather;
    }

    private ArrayList<Weather> mSavedWeather = new ArrayList<Weather>();

    public String getmHomeZipcode() {
        return mHomeZipcode;
    }

    public void putMoodWeather(String mood, String weather){
        mMoodWeather.put(mood,weather);
    }

    public String getWeather(String mood){
        return mMoodWeather.get(mood);
    }

    public void setmHomeZipcode(String mHomeZipcode) {
        this.mHomeZipcode = mHomeZipcode;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPushId(String pushId){
        this.pushId = pushId;
    }

}
