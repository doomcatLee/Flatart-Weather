package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.WeatherService;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity {

    ArrayList<Weather> mWeather = new ArrayList<>();
    public static final String TAG = ResultsActivity.class.getSimpleName();
    AppService service = new AppService();

    @Bind(R.id.weatherImageView) ImageView mWeatherImage;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ForecastListAdapter mAdapter;

    @Bind(R.id.tempTextView)
    TextView mTempTextView;

    @Bind(R.id.cityTextView) TextView mCityTextView;

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
                mWeather = weatherService.processResults(response);
                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String city = mWeather.get(0).getCity();
                        String currentDesc = mWeather.get(0).getDesc();
                        String currentIconID = mWeather.get(0).getIconID();

                        for (int i=0; i < mWeather.size();i++){
                            double temp = mWeather.get(i).getTemp();
                            String iconID = mWeather.get(i).getIconID();
                            String desc = mWeather.get(i).getDesc();
                        }
                        mTempTextView.setText(service.formatTemp(mWeather.get(0).getTemp()));
                        mAdapter = new ForecastListAdapter(getApplicationContext(), mWeather);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(ResultsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mCityTextView.setText(city);

                        service.setImageDynamic(mWeatherImage,currentIconID);

                    }

                });

            }

        });
    }
}
