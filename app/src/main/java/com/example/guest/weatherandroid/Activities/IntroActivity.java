package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    Button mGetStartedButton;
    TextView mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mGetStartedButton = (Button) findViewById(R.id.getStartedButton);
        mGetStartedButton.setOnClickListener(this);
        mLogin = (TextView) findViewById(R.id.loginTextView);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mGetStartedButton){
            Intent intent = new Intent(IntroActivity.this, Register1Activity.class);
            startActivity(intent);
        }

        if (v == mLogin){
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
