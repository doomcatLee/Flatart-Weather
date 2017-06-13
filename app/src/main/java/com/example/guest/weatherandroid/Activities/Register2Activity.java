package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{

    TextView mNextButton;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private String mEmail;
    AppService appService = new AppService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mNextButton = (TextView) findViewById(R.id.nextButton2);
        mNextButton.setOnClickListener(this);
        mPassword = (EditText) findViewById(R.id.passwordEditText);
        mPasswordConfirm = (EditText) findViewById(R.id.passwordConfirmEditText);
        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");
    }

    @Override
    public void onClick(View v) {
        if (v == mNextButton){
            if (appService.isPasswordValid(mPassword.getText().toString(), mPasswordConfirm.getText().toString(),this)){
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                intent.putExtra("password", mPassword.getText().toString());
                intent.putExtra("email", mEmail);
                startActivity(intent);
            }
        }
    }
}
