package com.example.guest.weatherandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.emailEditText) EditText mEmail;
    @Bind(R.id.nameEditText) EditText mName;
    @Bind(R.id.passwordEditText)
    EditText mPassword;
    @Bind(R.id.confirmPasswordEditText) EditText mPasswordConfirm;

    @Bind(R.id.createUserButton)
    Button mRegisterButton;


    private FirebaseAuth mAuth;
    AppService appService = new AppService();
    FirebaseService firebaseService = new FirebaseService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mRegisterButton){
            if(appService.isValidForm(mPassword, mPasswordConfirm, mEmail, this)){
                firebaseService.createNewUser(mName, mEmail, mPassword, mPasswordConfirm,mAuth, this);
            }else{
                System.out.print("ignore");
            }

        }
    }
}
