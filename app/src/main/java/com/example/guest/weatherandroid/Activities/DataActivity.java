package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

public class DataActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    BottomNavigationView bottomNavigationView;
    private TextView mUserEmail;
    private TextView mUserLocation;
    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mUserEmail = (TextView) findViewById(R.id.email);
        mUserLocation = (TextView) findViewById(R.id.locationTextView);

        //Shared Preferences here
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLocation = mSharedPreferences.getString("location", null);


        mUserLocation.setText(mLocation);

        bottomNavigationView  = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.action_home){
                            Intent intent1 = new Intent(DataActivity.this, ResultsActivity.class);
                            startActivity(intent1);
                        }else if(item.getItemId() == R.id.action_data){
//                            Intent intent2 = new Intent(DataActivity.this, DataActivity.class);
//                            startActivity(intent2);
                        }else if(item.getItemId() == R.id.action_search){
                            Intent intent3 = new Intent(DataActivity.this, MainActivity.class);
                            startActivity(intent3);
                        }else{

                        }
                        return true;
                    }
                });

    }


}
