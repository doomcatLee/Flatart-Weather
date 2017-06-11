package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guest.weatherandroid.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    Button mGetStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mGetStartedButton = (Button) findViewById(R.id.getStartedButton);
        mGetStartedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mGetStartedButton){
            Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
