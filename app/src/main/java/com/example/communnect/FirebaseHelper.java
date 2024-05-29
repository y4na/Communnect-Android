package com.example.communnect;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("favorite_news");
    }

    public void getAllFavoriteNews(final FirebaseCallback<List<FavoriteNews>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FavoriteNews> favoriteNewsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FavoriteNews favoriteNews = dataSnapshot.getValue(FavoriteNews.class);
                    if (favoriteNews != null) {
                        favoriteNewsList.add(favoriteNews);
                    }
                }
                callback.onCallback(favoriteNewsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}