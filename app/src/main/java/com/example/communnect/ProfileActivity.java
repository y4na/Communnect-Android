package com.example.communnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView setTextFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTextFullName=findViewById(R.id.txt_CompleteName);

        UserProfile userProfile = new UserProfile();
        userProfile.getFullname(new UserProfile.FullNameCallback(){

            @Override
            public void onCallback(String fullName) {
                if (fullName != null) {
                   //set Full name
                   setTextFullName.setText(fullName);
                } else {
                    // Handle the error.
                    System.out.println("Failed to retrieve full name.");
                }
            }
        });
    }
    // get Full name

}