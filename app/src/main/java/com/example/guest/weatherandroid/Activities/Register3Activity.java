package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Register3Activity extends AppCompatActivity implements View.OnClickListener {

    //Initialize FirebaseService

    private TextView mSubmitButton;
    private FirebaseAuth mAuth;

    private String mEmail;
    private EditText mLocation;
    private String mPassword;


    AppService appService = new AppService();
    FirebaseService firebaseService = new FirebaseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        mAuth = FirebaseAuth.getInstance();
        mSubmitButton = (TextView) findViewById(R.id.submitButton);
        mSubmitButton.setOnClickListener(this);

        Intent intent = getIntent();

        mLocation = (EditText) findViewById(R.id.location);
        mEmail = intent.getStringExtra("email");
        mPassword = intent.getStringExtra("password");
        Log.d("email", mEmail);
        Log.d("password", mPassword);
        Log.d("location", mLocation.getText().toString());

    }

    @Override
    public void onClick(View v) {
        if (v == mSubmitButton){
            firebaseService.createNewUser(mEmail, mPassword, mAuth, this);
            Intent intent = new Intent(Register3Activity.this, ResultsActivity.class);
            intent.putExtra("location", mLocation.getText().toString());
//            intent.putExtra("location", mEmail);
            Log.d("before click", mLocation.getText().toString());
            startActivity(intent);
        }
    }
}
