package com.example.guest.weatherandroid.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.weatherandroid.Constants;
import com.example.guest.weatherandroid.Model.User;
import com.example.guest.weatherandroid.Model.Weather;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;
import com.example.guest.weatherandroid.Services.FirebaseService;
import com.example.guest.weatherandroid.adapters.ForecastListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private ForecastListAdapter mAdapter;
    private User mUser;
    private FloatingActionButton mSaveButton;
    ArrayList<Weather> mWeather = new ArrayList<>();


    BottomNavigationView bottomNavigationView;

    //SERVICES
    FirebaseService firebaseService = new FirebaseService();
    AppService appService = new AppService();

    DatabaseReference mFirebaseReference;

    @Bind(R.id.weatherImageView)
    ImageView mWeatherImage;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tempTextView)
    TextView mTempTextView;

    @Bind(R.id.locationTextView)
    TextView mCityTextView;


    //Override so we add the button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        String location = intent.getStringExtra("zipcode");


        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
        private void logout() {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ResultsActivity.this, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        Log.d("location",location);
        mSaveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(this);

        mUser = new User();
        mUser.setmHomeZipcode(location);
//        mUser.setmEmail(email);

        bottomNavigationView  = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        if (item.getItemId() == R.id.action_home){
//                            Intent intent1 = new Intent(ResultsActivity.this, ResultsActivity.class);
//                            startActivity(intent1);
                        }else if(item.getItemId() == R.id.action_data){
                            Intent intent2 = new Intent(ResultsActivity.this, DataActivity.class);
                            intent2.putExtra("email", mUser.getmEmail());
                            intent2.putExtra("location", mUser.getmHomeZipcode());
                            startActivity(intent2);
                        }else if(item.getItemId() == R.id.action_search){
                            Intent intent3 = new Intent(ResultsActivity.this, MainActivity.class);
                            startActivity(intent3);
                        }else{

                        }
                        return true;
                    }
                });



        firebaseService.initiateService();
        mFirebaseReference = firebaseService.getLocationReference();

        appService.getWeather(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response)  {

                mWeather = appService.processResults(response);

                //Set mWeather here when the api call is made
                mUser.setmSavedWeather(mWeather);
                ResultsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Typeface robotoFont = Typeface.createFromAsset(getAssets(),"fonts/Roboto_Thin.ttf");
                        TextView[] viewList = {mTempTextView, mCityTextView};
                        appService.setFonts(viewList, robotoFont);

                        String currentDesc = mWeather.get(0).getDesc();
                        String currentIconID = mWeather.get(0).getIconID();
                        String city = mWeather.get(0).getCity();

                        mTempTextView.setText(appService.formatTemp(mWeather.get(0).getTemp()));
                        mAdapter = new ForecastListAdapter(getApplicationContext(), mWeather);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(ResultsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                        mCityTextView.setText(city);

                        appService.setImageDynamic(mWeatherImage,currentIconID);

                    }

                });

            }

        });

    }

    @Override
    public void onClick(View v){
        if(v == mSaveButton){
            DatabaseReference databaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_USER);
            databaseRef.push().setValue(mUser);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        }
    }

}
