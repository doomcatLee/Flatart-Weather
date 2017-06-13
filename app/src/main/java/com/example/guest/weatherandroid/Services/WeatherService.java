package com.example.guest.weatherandroid.Services;
import java.text.DateFormat;

import android.util.Log;

import com.example.guest.weatherandroid.Constants;
import com.example.guest.weatherandroid.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;


public class WeatherService {

    Weather weather;

    public static void getWeather (String location, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY_PARAMETER, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.YOUR_QUERY_PARAMETER, location);


        String url = urlBuilder.build().toString();
        Log.d("test", url);

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public ArrayList<Weather> processResults(Response response) {
        ArrayList<Weather> weatherForecastList = new ArrayList<>();
        try {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                System.out.println(jsonData);

                JSONObject forecastJSON = new JSONObject((jsonData));
                JSONArray listJSON = forecastJSON.getJSONArray("list");
                // get city
                JSONObject city = forecastJSON.getJSONObject("city");
                String cityName = city.getString("name");
                Log.d("cityName", cityName);

                for (int i =0; i < 7; i++){
                    JSONObject day = listJSON.getJSONObject(i);
                    int time = day.getInt("dt");
                    System.out.println(time);
                    //access temperatures here
                    JSONObject temp = day.getJSONObject("temp");
                    double maxTemp = temp.getDouble("max");
                    double minTemp = temp.getDouble("min");
                    double currentTemp = temp.getDouble("day");

                    //access weather List here
                    JSONArray weatherList = day.getJSONArray("weather");
                    JSONObject weatherDescriptions = weatherList.getJSONObject(0);
                    String iconID = weatherDescriptions.getString("icon");
                    String description = weatherDescriptions.getString("description");
                    // access wind here
                    double windSpeed = day.getDouble("speed");

                    Weather newForecast = new Weather (cityName, currentTemp, iconID, description, time, maxTemp, minTemp, windSpeed);
                    System.out.println(newForecast);

                    weatherForecastList.add(newForecast);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherForecastList;
    }

}
