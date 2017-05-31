package com.example.guest.weatherandroid.Services;


import com.example.guest.weatherandroid.Model.Weather;

import java.text.DecimalFormat;

public class AppService {

    public AppService(){

    }

    public String formatTemp(double num){
        DecimalFormat df = new DecimalFormat("#");
        String str = df.format(num) + " °F";
        return str;
    }
}
