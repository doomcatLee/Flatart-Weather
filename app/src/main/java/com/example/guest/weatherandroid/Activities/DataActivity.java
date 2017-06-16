package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.guest.weatherandroid.Constants;
import com.example.guest.weatherandroid.Model.User;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class DataActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    BottomNavigationView bottomNavigationView;
    private TextView mUserLocation;
    private TextView mUserEmail;
    private String mLocation;
    private User mUser;
    private ValueEventListener mUserReferenceListener;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        //SHARED PREFERENCES STUFF
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        //FIREBASE STUFF
        mUserReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_USER);
        mUserReferenceListener = mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Map<String, Object> item = (Map<String, Object>)child.getValue();

                    String email = (String) item.get("mEmail");
                    String zipcode = (String) item.get("mHomeZipcode");

                    mLocation = zipcode;
                    mUserEmail.setText(email);
                    mUserLocation.setText(zipcode);
                    mEditor.putString("userZipcode", zipcode).apply();

                    Log.d("CURRENT ZIPCODE : ", zipcode);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Shared Preferences here
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        mUserLocation = (TextView) findViewById(R.id.locationTextView);
        mUserEmail = (TextView) findViewById(R.id.emailTextView);




        mUserLocation.setText(mLocation);


        bottomNavigationView  = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.action_home){
                            Intent intent1 = new Intent(DataActivity.this, ResultsActivity.class);
                            startActivity(intent1);
                            overridePendingTransition(R.animator.flip_start, R.animator.flip_end);
                        }else if(item.getItemId() == R.id.action_data){
//                            Intent intent2 = new Intent(DataActivity.this, DataActivity.class);
//                            startActivity(intent2);
                        }else if(item.getItemId() == R.id.action_search){
                            Intent intent3 = new Intent(DataActivity.this, MainActivity.class);
                            startActivity(intent3);
                            overridePendingTransition(R.animator.flip_start, R.animator.flip_end);
                        }else{

                        }
                        return true;
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserReference.removeEventListener(mUserReferenceListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DataActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}
