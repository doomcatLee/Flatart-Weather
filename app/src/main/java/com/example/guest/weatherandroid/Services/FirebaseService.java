package com.example.guest.weatherandroid.Services;


import android.util.Log;

import com.example.guest.weatherandroid.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseService {
    private ValueEventListener mSearchedLocationReferenceListener;
    private DatabaseReference mSearchedLocationReference;

    public FirebaseService(){

    }

    public DatabaseReference getLocationReference(){
        return mSearchedLocationReference;
    }

    public ValueEventListener getLocationReferenceListener(){
        return mSearchedLocationReferenceListener;
    }

    public void initiateService(){
        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);

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



    public void saveLocationToFirebase(String location, DatabaseReference dr) {
        dr.push().setValue(location);
    }
}
