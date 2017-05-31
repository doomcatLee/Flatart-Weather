package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.WeatherService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity {

    Weather weather;
    public static final String TAG = ResultsActivity.class.getSimpleName();

    @Bind(R.id.weatherImageView)
    ImageView mWeatherImageView;

    @Bind(R.id.tempTextView)
    TextView mTempTextView;

    @Bind(R.id.descTextView) TextView mDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        getWeather(location);
    }

    private void getWeather(String location){
        final WeatherService weatherService = new WeatherService();
        weatherService.getWeather(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response)  {
                weather = weatherService.processResults(response);
                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String city = weather.getCity();
                        double temp = weather.getTemp();
                        String iconID = weather.getIconID();
                        String desc = weather.getDesc();
                        mTempTextView.setText(Double.toString(temp) + "°F");
                        mDescTextView.setText("Current weather is " + desc + " in " + city);
                    }

                });

            }

        });
    }
}
