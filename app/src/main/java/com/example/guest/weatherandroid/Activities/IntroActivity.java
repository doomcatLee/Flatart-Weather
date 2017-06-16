package com.example.guest.weatherandroid.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener{

    Button mGetStartedButton;
    TextView mLogin;
    private View mIntroView;
    private View mRegisterView;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mGetStartedButton = (Button) findViewById(R.id.getStartedButton);
        mGetStartedButton.setOnClickListener(this);
        mLogin = (TextView) findViewById(R.id.loginTextView);
        mLogin.setOnClickListener(this);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mIntroView = inflater.inflate(R.layout.activity_intro, null);
        mRegisterView = inflater.inflate(R.layout.activity_register, null);

        // Initially hide the content view.
        mIntroView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);



    }

    @Override
    public void onClick(View v) {
        if (v == mGetStartedButton){
            Intent intent = new Intent(IntroActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

        if (v == mLogin){
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }



}
