package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.locationEditText)
    EditText mLocationEditText;
    @Bind(R.id.getWeatherButton)
    Button mGetWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGetWeatherButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if( v == mGetWeatherButton ){
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent (MainActivity.this, ResultsActivity.class);
            intent.putExtra("location", location);
            startActivity(intent);

        }
    }
}
