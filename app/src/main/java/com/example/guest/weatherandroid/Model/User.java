package com.example.guest.weatherandroid.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String mHomeZipcode;
    private String mEmail;
    private String pushId;
    private Map<String, String> mMoodWeather = new HashMap<String, String>();

    public User(){
    }

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
