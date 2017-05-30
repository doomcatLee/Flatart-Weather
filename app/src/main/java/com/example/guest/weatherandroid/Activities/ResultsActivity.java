package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultsActivity extends AppCompatActivity {
    @Bind(R.id.weatherImageView)
    ImageView mWeatherImageView;
    @Bind(R.id.tempTextView)
    TextView mTempTextView;
    @Bind(R.id.cityTextView) TextView mCityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mTempTextView.setText(intent.getStringExtra("location"));
    }
}
