package com.example.guest.weatherandroid.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.weatherandroid.Activities.IntroActivity;
import com.example.guest.weatherandroid.Activities.RegisterActivity;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;

import butterknife.ButterKnife;

/**
 * Created by Gun Lee.
 */

public class EmailFragment extends Fragment implements View.OnClickListener{

    AppService service = new AppService();

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;




    private TextView mNextButton1;
    private ImageView mBackButton;
    private EditText mEmail;

    private static final String TAG = EmailFragment.class.getSimpleName();

    public EmailFragment(){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPref.edit();

        ButterKnife.bind(getActivity());
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        mNextButton1 = (TextView) view.findViewById(R.id.btnNext2);
        mBackButton = (ImageView) view.findViewById(R.id.btnBack);
        mEmail = ((EditText) view.findViewById(R.id.etEmail));


        mNextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mNextButton1) {
                    if(service.isEmailValid(mEmail.getText().toString(), getActivity())){
                        addToSharedPreferences(mEmail.getText().toString());
                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
                        intent.putExtra("showPasswordFragment", "1");
                        startActivity(intent);
                    }else{
                        service.applyShakeAnimation(mEmail);
                    }

                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(v == mBackButton){
                    Intent intent = new Intent(getActivity(), IntroActivity.class);
                    startActivity(intent);
                }
            }
        });

        Log.d(TAG, "onCreateView: ends");
        return view;
    }


    @Override
    public void onClick(View v) {

    }

    private void addToSharedPreferences(String email) {
        mEditor.putString("userEmail", email).apply();
    }

}