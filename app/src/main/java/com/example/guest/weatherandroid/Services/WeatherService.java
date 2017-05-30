package com.example.guest.weatherandroid.Services;

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

    public Weather processResults(Response response) {
//        Weather weather = new Weather("", 2.31, "", "");

        try {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();
                JSONObject weatherJSON = new JSONObject(jsonData);
                String city = weatherJSON.getString("name");
                weather = new Weather(city, 2.231, "test", "test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

}
