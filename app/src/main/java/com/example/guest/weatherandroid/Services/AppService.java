package com.example.guest.weatherandroid.Services;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;

import java.text.DecimalFormat;




public class AppService extends WeatherService{

    public AppService(){

    }

    public String formatTemp(double num){
        DecimalFormat df = new DecimalFormat("#");
        String str = df.format(num) + " °F";
        return str;
    }


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

    public void setFonts(TextView[] views, Typeface font){
        for (TextView i: views){
            i.setTypeface(font);
        }
    }
}
