package com.example.guest.weatherandroid.Services;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guest.weatherandroid.Activities.RegisterActivity;
import com.example.guest.weatherandroid.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class FirebaseService {
    private ValueEventListener mReferenceListener;
    private DatabaseReference mSearchedLocationReference;
    private DatabaseReference mUserReference;

    public FirebaseService(){

    }

    public void createNewUser(EditText a, EditText b, EditText c, EditText d, FirebaseAuth auth, Activity activity) {
        final String name = a.getText().toString().trim();
        final String email = b.getText().toString().trim();
        String password = c.getText().toString().trim();
        String confirmPassword = d.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successful");
                        } else {
                            System.out.println("Didn't Work");
                        }
                    }
                });
    }


    //Starting and adding firebase
    public void initiateService(){
        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

        mUserReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_USER);

        //Adding FIREBASE LISTENER HERE
        mSearchedLocationReference.addValueEventListener(new ValueEventListener() {

            //THIS BASICALLY RETREIVES DATA FROM FIREBASE
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location); //log
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    public void saveObjectToFirebase(Object o){
        mUserReference.push().setValue(o);
    }

    //GETTERS AND SETTERS
    public DatabaseReference getLocationReference(){
        return mSearchedLocationReference;
    }

    public DatabaseReference getmUserReference(){
        return mUserReference;
    }

    public ValueEventListener getReferenceListener(){
        return mReferenceListener;
    }
}
