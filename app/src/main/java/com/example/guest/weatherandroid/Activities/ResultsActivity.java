package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.example.guest.weatherandroid.Services.WeatherService;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity {

    AppService appService = new AppService();
    private ForecastListAdapter mAdapter;

    //SERVICES
    ArrayList<Weather> mWeather = new ArrayList<>();
    WeatherService weatherService = new WeatherService();
    FirebaseService firebaseService = new FirebaseService();

    DatabaseReference mFirebaseReference;

    @Bind(R.id.weatherImageView)
    ImageView mWeatherImage;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tempTextView)
    TextView mTempTextView;

    @Bind(R.id.cityTextView)
    TextView mCityTextView;


    //Override so we add the button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        if (item.getItemId() == R.id.saveButton){
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
                            appService.setFonts(viewList, robotoFont);

                            String currentDesc = mWeather.get(0).getDesc();
                            String currentIconID = mWeather.get(0).getIconID();
                            String city = mWeather.get(0).getCity();

                            mTempTextView.setText(appService.formatTemp(mWeather.get(0).getTemp()));
                            mAdapter = new ForecastListAdapter(getApplicationContext(), mWeather);
                            mRecyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager =
                                    new LinearLayoutManager(ResultsActivity.this);
                            mRecyclerView.setLayoutManager(layoutManager);
                            mRecyclerView.setHasFixedSize(true);
                            mCityTextView.setText(city);

                            appService.setImageDynamic(mWeatherImage,currentIconID);
                            firebaseService.saveLocationToFirebase(city);
                            Log.d("It DID", "WORK");
                            Log.d("variables", String.format("%s,%s",city,currentDesc));

                        }

                    });

                }

            });
        }else{
            Log.d("didnt", "WORK");
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        firebaseService.initiateService();
        mFirebaseReference = firebaseService.getLocationReference();

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
                        appService.setFonts(viewList, robotoFont);

                        String currentDesc = mWeather.get(0).getDesc();
                        String currentIconID = mWeather.get(0).getIconID();
                        String city = mWeather.get(0).getCity();

                        mTempTextView.setText(appService.formatTemp(mWeather.get(0).getTemp()));
                        mAdapter = new ForecastListAdapter(getApplicationContext(), mWeather);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(ResultsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mCityTextView.setText(city);

                        appService.setImageDynamic(mWeatherImage,currentIconID);

                    }

                });

            }

        });

    }

}
