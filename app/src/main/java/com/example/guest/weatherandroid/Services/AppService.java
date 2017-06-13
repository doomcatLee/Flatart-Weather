package com.example.guest.weatherandroid.Services;


import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;

import java.text.DecimalFormat;

public class AppService extends WeatherService{

    public AppService(){

    }

    /**
     * Validates user input form
     * Args: (EditText) password, (EditText) passwordConfirm, (EditText) email and Activity
     * Returns: (Boolean) true or false
     */

    public Boolean isEmailValid(String e, Activity activity){
        if (e.contains("@")){
            return true;
        }else{
            Toast.makeText(activity, "Email must contain @", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public Boolean isPasswordValid(String p1,String p2, Activity activity){
        if (p1.length() > 5 && p2.length() > 5){
            if (p1.equals(p2)){
                return true;
            }
            Toast.makeText(activity, "Both passwords must match", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(activity, "Password must be longer than " + p1.length() + " digits", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Formats temperature from Kelvin to Fahrenheit with concatenated string
     * Args: (double) Kelvin
     * Returns: (String) Fahrenheit
     */
    public String formatTemp(double num){
        DecimalFormat df = new DecimalFormat("#");
        String str = df.format(num) + " °F";
        return str;
    }

    /**
     * Based on the iconID retrieved, change view to its background
     * Args: ImageView and (String) iconID
     * Returns: nothing
     */
    public void setImageDynamic(ImageView view, String iconID){

        if (iconID.equals("01d") || iconID.equals("01n")){
            view.setImageResource(R.drawable.sunny);
        }else if(iconID.equals("10d") || iconID.equals("10n") || iconID.equals("09n") || iconID.equals("09d")){
            view.setImageResource(R.drawable.rain);
        }else if(iconID.equals("02d") || iconID.equals("02n")){
            view.setImageResource(R.drawable.few_clouds);
        }else if(iconID.equals("04d") || iconID.equals("04n") || iconID.equals("03n") || iconID.equals("03d")){
            view.setImageResource(R.drawable.more_clouds);
        }else if(iconID.equals("13d") || iconID.equals("13n")){
            view.setImageResource(R.drawable.snow);
        }else if(iconID.equals("11d") || iconID.equals("11n")){
            view.setImageResource(R.drawable.heavy_rain);
        }else if(iconID.equals("50d") || iconID.equals("50n")){
            view.setImageResource(R.drawable.mist);
        }else{
            System.out.println("uhhhhhhhhhhhhhhhh");
        }
    }

    /**
     * Set Typeface for all TextView in TextView ArrayList
     * Args: (TextView[]) ArrayList of TextViews
     * Returns: nothing
     */
    public void setFonts(TextView[] views, Typeface font){
        for (TextView i: views){
            i.setTypeface(font);
        }
    }
}
