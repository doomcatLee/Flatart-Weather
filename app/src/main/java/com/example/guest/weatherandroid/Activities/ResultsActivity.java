package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Constants;
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

    @Bind(R.id.tempTextView)
    TextView mTempTextView;

    @Bind(R.id.cityTextView) TextView mCityTextView;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SharedPreferences mSharedPreferences;
    private ForecastListAdapter mAdapter;
    private String mLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLocation = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        //TESTING
        Log.d("Shared Pref Location", mLocation);
        if (mLocation != null) {
            getWeather(mLocation);
        }

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

                        Typeface robotoFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto_Thin.ttf");
                        TextView[] viewList = {mTempTextView, mCityTextView};
                        service.setFonts(viewList, robotoFont);

                        String currentDesc = mWeather.get(0).getDesc();
                        String currentIconID = mWeather.get(0).getIconID();
                        String city = mWeather.get(0).getCity();

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
