package com.example.guest.weatherandroid.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private ProgressDialog mAuthProgressDialog;

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

        createAuthProgressDialog();

        mAuth = FirebaseAuth.getInstance();
        mSubmitButton = (TextView) findViewById(R.id.submitButton);
        mSubmitButton.setOnClickListener(this);

        //Shared Preferences here
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();


        Intent intent = getIntent();
        mLocation = (EditText) findViewById(R.id.location);
        mEmail = intent.getStringExtra("email");
        mPassword = intent.getStringExtra("password");
        Log.d("email", mEmail);
        Log.d("password", mPassword);
        Log.d("location", mLocation.getText().toString());

    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }


    @Override
    public void onClick(View v) {
        if (v == mSubmitButton){
            mAuthProgressDialog.show();
            firebaseService.createNewUser(mEmail, mPassword, mAuth, this);
            addToSharedPreferences(mLocation.getText().toString(),mEmail);


            Intent intent = new Intent(Register3Activity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String location,String email) {
        mEditor.putString("location", location).apply();
        mEditor.putString("email", email).apply();
    }
}
