package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NewsDeatilActivity extends AppCompatActivity {

    String title, desc, content, imageURL, url;
    private TextView titleTV, subDescTV, contentTV;
    private ImageView newsIV, favoritesIV;
    private Button readNewsTV;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_deatil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get Firebase Database reference for current user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(MainActivity.UserId);


        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        titleTV = findViewById(R.id.idTVTitle);
        subDescTV = findViewById(R.id.idTVSubDesc);
        contentTV = findViewById(R.id.idTVContent);
        newsIV = findViewById(R.id.idIVNews);
        readNewsTV = findViewById(R.id.idBtnReadNews);
        favoritesIV = findViewById(R.id.idIVFavorites);

        titleTV.setText(title);
        subDescTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newsIV);

        readNewsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsDeatilActivity.this, NewsFullActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            }
        });

        favoritesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    // Unmark the article as favorite
                    unmarkAsFavorite();
                } else {
                    // Mark the article as favorite
                    markAsFavorite();
                }
            }
        });
        if (isFavorite()) {
            favoritesIV.setImageResource(R.drawable.favorite_filled);
            isFavorite = true;
        }

        checkIfFavorite();


    }

    private void checkIfFavorite() {
        // Retrieve the user's favorite news from the database
        userRef.child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String favoriteUrl = snapshot.getValue(String.class);
                    // Compare the URL of the current news item with the URLs of the user's favorite news
                    if (favoriteUrl != null && favoriteUrl.equals(url)) {
                        // If the current news item is among the favorites, mark it as favorite in the UI
                        favoritesIV.setImageResource(R.drawable.favorite_filled);
                        isFavorite = true;
                        return;
                    }
                }
                // If the current news item is not among the favorites, keep it as not favorite
                favoritesIV.setImageResource(R.drawable.favorite_border);
                isFavorite = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }

    private void markAsFavorite() {
        // Your code to save the article as favorite in Firebase
        // After successful marking, set isFavorite to true
        userRef.child("favorites").push().setValue(url)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(NewsDeatilActivity.this, "Marked as Favorite", Toast.LENGTH_SHORT).show();
                    favoritesIV.setImageResource(R.drawable.favorite_filled);
                    isFavorite = true;
                })
                .addOnFailureListener(e -> Toast.makeText(NewsDeatilActivity.this, "Failed to mark as favorite", Toast.LENGTH_SHORT).show());
    }

    private void unmarkAsFavorite() {
        // Your code to remove the article from favorites in Firebase
        // After successful unmarking, set isFavorite to false
        // For demonstration, I assume you have a method to retrieve the key of the saved news
        userRef.child("favorites").orderByValue().equalTo(url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    Toast.makeText(NewsDeatilActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                    favoritesIV.setImageResource(R.drawable.favorite_border);
                    isFavorite = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
    }
    private boolean isFavorite() {
        // Your code to check if the article is already marked as favorite in Firebase
        // Return true if it is marked as favorite, otherwise return false
        // For demonstration, return false by default
        return isFavorite;
    }
}
