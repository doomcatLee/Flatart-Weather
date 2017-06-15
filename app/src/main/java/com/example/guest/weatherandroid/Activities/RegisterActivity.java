package com.example.guest.weatherandroid.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.fragment.EmailFragment;
import com.example.guest.weatherandroid.fragment.PasswordFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    /**Shared Preferences here*/
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    /**
     * Fragment Setup
     * */
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    /**
     * Wise Commute Fragments
     * */

    private PasswordFragment mPasswordFragment;
    private EmailFragment mEmailFragment;


    /**
     * Initialize Fragment trigger
     * 0 = false, 1 = true
     * */
    String showPasswordFragment = "0";
    String isFormDone = "0";
    String passwordBackClicked = "0";

    /** User properties go here */
    private String userEmail;
    private String userPassword;
    private String userPasswordConfirm;

    private DatabaseReference userAccounts;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /** Progress Dialog */
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: starts");


        /**
         * Instantiate form fragments
         **/
        fragmentManager = getSupportFragmentManager();
        mPasswordFragment = new PasswordFragment();
        mEmailFragment = new EmailFragment();


        /**
         * Grab fragment string triggers from individual intents
         * */
        Intent i = getIntent();
        showPasswordFragment = i.getStringExtra("showPasswordFragment");
        isFormDone = i.getStringExtra("isFormDone");
        passwordBackClicked = i.getStringExtra("passwordBackClicked");


        /** Grab user information using Shared Preferences* */
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        userEmail = mSharedPref.getString("userEmail", null);
        userPassword = mSharedPref.getString("userPassword",null);
        userPasswordConfirm = mSharedPref.getString("userPasswordConfirm",null);


        Log.d(TAG, "VALUES PASSED FROM INTENT" + userEmail + userPassword + userPasswordConfirm);

        /**
         * By Default, always start EmailFormFragment as the main fragment for register
         * */
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, mEmailFragment);
        transaction.commit();
        Log.d(TAG, "onCreate: TRANSACTION REPLACE PASSWORD FORM FRAGMENT");

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createAuthProgressDialog();

        /** Assigning the Firebase Reference, basically we are telling Firebase where are
         * starting point should be inside our database. For this use case, we want the
         * userAccount variable to hold the reference to the users section of our database. */
        userAccounts = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users");

        /**
         * When the form is complete, trigger this condition
         * */
        if(isFormDone != null){
            if(isFormDone.equals("1")){
                createNewUser();

            }else{
                Log.d(TAG, "onCreate: WORK FRAGMENT IS NULL");
            }
        }

        /**
         * Home Backbutton If conditions
         * */
//        if(workBackClicked != null){
//            if(workBackClicked.equals("1")){
//                Log.d(TAG, "onCreate: WORK BACK BUTTON CLICKED");
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.registerContent, mHomeFormFragment);
//                transaction.commit();
//            }
//        }
        /**
         * Work Fragment if condition
         * */
//        if(showWorkFragment != null){
//            if(showWorkFragment.equals("1")){
//                Log.d(TAG, "onCreate: WORK FRAGMENT EQUALS 1 AND PROCESSING");
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.registerContent, mWorkFormFragment);
//                transaction.commit();
//            }
//        }else{
//            Log.d(TAG, "onCreate: WORK FRAGMENT IS NULL");
//        }


        /**
         * Home Backbutton If conditions
         * */
//        if(homeBackClicked != null){
//            if(homeBackClicked.equals("1")){
//                Log.d(TAG, "onCreate: HOME BACK BUTTON CLICKED");
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.registerContent, mPasswordFormFragment);
//                transaction.commit();
//            }
//        }
        /**
         * Home Fragment If conditions
         * */
//        if(showHomeFragment != null){
//            if(showHomeFragment.equals("1")){
//                Log.d(TAG, "onCreate: HOME FRAGMENT EQUALS 1 AND PROCESSING");
//                transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.registerContent, mHomeFormFragment);
//                transaction.commit();
//            }
//        }else{
//            Log.d(TAG, "onCreate: HOME FRAGMENT IS NULL");
//        }

        /**
         * If condition for PASSWORD FRAGMENT
         * */
        if(showPasswordFragment != null) {
            if (showPasswordFragment.equals("1")) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, mPasswordFragment);
                transaction.commit();
            } else if (passwordBackClicked.equals("1")){
                Log.d(TAG, "onCreate: PASSWORD BACK HAS BEEN CLICKED");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, mEmailFragment);
                transaction.commit();
            }else{
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content, mEmailFragment);
                transaction.commit();
            }
        }
        Log.d(TAG, "onCreate: ends");
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart: starts");
//        mAuth.addAuthStateListener(mAuthListener);
//        Log.d(TAG, "onStart: ends");
//    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: starts");
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        Log.d(TAG, "onStop: ends");
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {

    }

    private void createNewUser() {
        Log.d(TAG, "createNewUser: "+ userEmail);
        Log.d(TAG, "createNewUser: "+ userPassword);
        String email = userEmail;
        String password = userPassword;
        String password2 = userPasswordConfirm;

        mAuthProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication is successful");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userEmail = user.getEmail();
                            String userName = user.getDisplayName();
                            String userUid = user.getUid();
                            Uri userProfileImage = user.getPhotoUrl();

//                            User newUser = new User(userEmail, userName, userUid, userProfileImage);
//                            saveUserToFirebase(newUser);
                            mAuthProgressDialog.dismiss();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

//    @Override
//    public void saveUserToFirebase(User user) {
//        /** Store the user object in our users section of our database */
//        userAccounts.push().setValue(user);
//    }
}
