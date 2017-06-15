package com.example.guest.weatherandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.example.guest.weatherandroid.Activities.DataActivity;
import com.example.guest.weatherandroid.Activities.RegisterActivity;
import com.example.guest.weatherandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZipcodeFragment extends Fragment {

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    private static final String TAG = PasswordFragment.class.getSimpleName();
    private ImageView mBackButton;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    @Bind(R.id.btnFinish) TextView mFinishButton;

    public ZipcodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        View view = inflater.inflate(R.layout.fragment_zipcode, container, false);

        mFinishButton = (TextView) view.findViewById(R.id.btnFinish);
        mBackButton = (ImageView) view.findViewById(R.id.btnBack);


        //Shared Prefences call onCreateView
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPref.edit();

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("zipcodeBackClicked", "1");
                startActivity(intent);
            }
        });


        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mFinishButton) {
                    Intent intent = new Intent(getActivity(), DataActivity.class);
                    intent.putExtra("showZipcodeFragment", "1");
                    startActivity(intent);
                }
            }
        });

        return view;

    }

    private void addToSharedPreferences(String a, String b) {
        mEditor.putString("userPassword", a).apply();
        mEditor.putString("userPasswordConfirm", b).apply();
    }

}