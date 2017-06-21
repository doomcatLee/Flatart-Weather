package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.locationEditText2)
    EditText mLocationEditText;
    @Bind(R.id.getStartedButton)
    Button mGetWeatherButton;
    @Bind(R.id.weather)
    TextView mOpenWeather;

    //Initialize FirebaseService
    FirebaseService fbService = new FirebaseService();
    ValueEventListener mSearchedLocationReferenceListener;
    DatabaseReference mSearchedLocationReference;
    AppService appService = new AppService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGetWeatherButton.setOnClickListener(this);
        mOpenWeather.setOnClickListener(this);

        //Firebase
        fbService.initiateService();
        mSearchedLocationReferenceListener = fbService.getReferenceListener();
        mSearchedLocationReference = fbService.getLocationReference();

    }

    //When the app is off
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }

    @Override
    public void onClick(View v) {
        if( v == mGetWeatherButton ){
            if(appService.isZipcodeValid(mLocationEditText.getText().toString(), this)){
                String location = mLocationEditText.getText().toString();
                Intent intent = new Intent (MainActivity.this, ResultsActivity.class);
                intent.putExtra("location", location);
                System.out.println("MAINACTIVITY LOCATION: location " + location);
                fbService.saveLocationToFirebase(location);
                startActivity(intent);
                overridePendingTransition(R.animator.flip_start, R.animator.flip_end);
            }else{

            }


        }

        if (v== mOpenWeather){

            Uri webpage = Uri.parse("https://openweathermap.org/");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        }
    }

}
