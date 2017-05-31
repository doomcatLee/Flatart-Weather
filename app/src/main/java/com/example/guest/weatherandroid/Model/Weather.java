package com.example.guest.weatherandroid.Model;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {

    private String mCity;
    private double mKelvin;
    private String mIconID;
    private String mDesc;
    private long mTime;
    private double mMaxTemp;
    private double mMinTemp;
    private double mWindSpeed;

    public Weather(String city, double kelvin, String iconID, String desc, long time, double maxTemp, double minTemp, double windSpeed){
        this.mCity = city;
        this.mKelvin = kelvin;
        this.mIconID = iconID;
        this.mDesc = desc;
        this.mTime = time;
        this.mMaxTemp = maxTemp;
        this.mMinTemp = minTemp;
        this.mWindSpeed = windSpeed;
    }

    public String getCity() {
        return mCity;
    }

    public double getTemp() {
        return ((mKelvin * 1.8) - 459.67);
    }

    public String getIconID() {
        return mIconID;
    }

    public String getDesc() {
        return mDesc;
    }

    public String getTime() {
        Date date = new Date(mTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E", Locale.US);
        return format.format(date);

    }
    public double getMaxTemp() {
        return mMaxTemp;
    }
    public double getMinTemp() {
        return mMinTemp;
    }
    public double getWindSpeed() {
        return mWindSpeed;
    }




}
