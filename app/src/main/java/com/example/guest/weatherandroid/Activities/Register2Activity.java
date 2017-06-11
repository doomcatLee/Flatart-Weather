package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{

    TextView mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mNextButton = (TextView) findViewById(R.id.nextButton2);
        mNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mNextButton){
            if (v == mNextButton){
                Intent intent = new Intent(Register2Activity.this, ResultsActivity.class);
                startActivity(intent);
            }
        }
    }
}
