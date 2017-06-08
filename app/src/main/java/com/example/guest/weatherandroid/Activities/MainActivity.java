package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.weatherandroid.Constants;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.locationEditText)
    EditText mLocationEditText;
    @Bind(R.id.getWeatherButton)
    Button mGetWeatherButton;
    @Bind(R.id.openWeather)
    TextView mOpenWeather;


    private DatabaseReference mSearchedLocationReference;

    //SHARED PREFERENCES
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        mEditor = mSharedPreferences.edit();

        mGetWeatherButton.setOnClickListener(this);
        mOpenWeather.setOnClickListener(this);

//        mSearchedLocationReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

    }


    @Override
    public void onClick(View v) {
        if( v == mGetWeatherButton ){
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent (MainActivity.this, ResultsActivity.class);
            intent.putExtra("location", location);
            addToSharedPreferences(location);
            //TESTING
            Log.d("Shared Pref Location", mRecentAddress);
//            saveLocationToFirebase(location);
            startActivity(intent);

        }

        if (v== mOpenWeather){

            Uri webpage = Uri.parse("https://openweathermap.org/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        }
    }

    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.setValue(location);
    }

    //SHARED PREFERECNES METHOD
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

}
