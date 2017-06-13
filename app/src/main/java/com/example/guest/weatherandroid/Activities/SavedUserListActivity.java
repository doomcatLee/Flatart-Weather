package com.example.guest.weatherandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.guest.weatherandroid.Constants;
import com.example.guest.weatherandroid.Model.User;
import com.example.guest.weatherandroid.R;
import com.example.guest.weatherandroid.adapters.FirebaseUserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedUserListActivity extends AppCompatActivity {
    private DatabaseReference mUserReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        mUserReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_USER);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<User, FirebaseUserViewHolder>
                (User.class, R.layout.user_list_item, FirebaseUserViewHolder.class,
                        mUserReference) {

            @Override
            protected void populateViewHolder(FirebaseUserViewHolder viewHolder,
                                              User model, int position) {
                viewHolder.bindUser(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
