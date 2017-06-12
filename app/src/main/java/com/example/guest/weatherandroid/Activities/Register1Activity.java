package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;

public class Register1Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private TextView mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        mNextButton = (TextView) findViewById(R.id.nextButton1);
        mEmail = (EditText) findViewById(R.id.email);
        mNextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mNextButton){
            if (v == mNextButton){
                Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                intent.putExtra("email",mEmail.getText().toString());
                startActivity(intent);
            }
        }
    }
}
