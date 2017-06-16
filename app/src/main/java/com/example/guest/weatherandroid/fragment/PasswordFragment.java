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

import com.example.guest.weatherandroid.Activities.RegisterActivity;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.Services.AppService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PasswordFragment extends Fragment {

    AppService service = new AppService();

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;

    private static final String TAG = PasswordFragment.class.getSimpleName();
    private ImageView mBackButton;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    @Bind(R.id.btnNext2) TextView mNextButton;

    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        mNextButton = (TextView) view.findViewById(R.id.btnNext2);
        mBackButton = (ImageView) view.findViewById(R.id.btnBack);
        mPassword = (EditText) view.findViewById(R.id.etPassword);
        mPasswordConfirm = (EditText) view.findViewById(R.id.etPasswordConfirm);

        //Shared Prefences call onCreateView
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPref.edit();

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra("passwordBackClicked", "1");
                startActivity(intent);
            }
        });


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mNextButton) {
                    if(service.isPasswordValid(mPassword.getText().toString(), mPasswordConfirm.getText().toString(),getActivity())){
                        Log.d(TAG, "PASSWORD FRAG " + mPassword.getText().toString() + mPasswordConfirm.getText().toString() );
                        addToSharedPreferences(mPassword.getText().toString(), mPasswordConfirm.getText().toString());
                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
                        intent.putExtra("showZipcodeFragment", "1");
                        startActivity(intent);
                    }else{
                        service.applyShakeAnimation(mPassword);
                        service.applyShakeAnimation(mPasswordConfirm);
                    }
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