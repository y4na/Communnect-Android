package com.example.communnect;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile {
    public interface FullNameCallback {
        void onCallback(String fullName);
    }
    public void getFullname(final FullNameCallback callback){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String familyName = dataSnapshot.child("lastname").getValue(String.class);
                String fullName = name + " " + familyName;

                callback.onCallback(fullName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                callback.onCallback(null);
            }
        });
    }
}
