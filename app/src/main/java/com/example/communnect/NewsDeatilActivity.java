//package com.example.communnect;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//public class NewsDeatilActivity extends AppCompatActivity {
//
//    String title, desc, content, imageURL, url;
//    private TextView titleTV, subDescTV, contentTV;
//    private ImageView newsIV, favoritesIV;
//    private Button readNewsTV;
//    private DatabaseReference userFavoritesRef;
//    private FirebaseAuth mAuth;
//    private boolean isFavorite = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_news_deatil);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        // Get Firebase Database reference for current user's favorites
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        if (mAuth.getCurrentUser() != null) {
//            userFavoritesRef = database.getReference("users").child(mAuth.getCurrentUser().getUid()).child("favorites");
//        } else {
//            // Handle the case when the user is not authenticated
//            // You might want to redirect the user to the login screen
//        }
//
//        title = getIntent().getStringExtra("title");
//        desc = getIntent().getStringExtra("desc");
//        content = getIntent().getStringExtra("content");
//        imageURL = getIntent().getStringExtra("image");
//        url = getIntent().getStringExtra("url");
//
//        titleTV = findViewById(R.id.idTVTitle);
//        subDescTV = findViewById(R.id.idTVSubDesc);
//        contentTV = findViewById(R.id.idTVContent);
//        newsIV = findViewById(R.id.idIVNews);
//        readNewsTV = findViewById(R.id.idBtnReadNews);
//        favoritesIV = findViewById(R.id.idIVFavorites);
//
//        titleTV.setText(title);
//        subDescTV.setText(desc);
//        contentTV.setText(content);
//        Picasso.get().load(imageURL).into(newsIV);
//
//        readNewsTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(NewsDeatilActivity.this, NewsFullActivity.class);
//                i.putExtra("url", url);
//                startActivity(i);
//            }
//        });
//
//        favoritesIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isFavorite) {
//                    // Unmark the article as favorite
//                    unmarkAsFavorite();
//                } else {
//                    // Mark the article as favorite
//                    markAsFavorite();
//                }
//            }
//        });
//
//        checkIfFavorite();
//    }
//
//    private void checkIfFavorite() {
//        if (userFavoritesRef != null) {
//            userFavoritesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    isFavorite = dataSnapshot.hasChild(url);
//                    updateFavoriteIcon();
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Handle onCancelled
//                }
//            });
//        }
//    }
//
//    private void markAsFavorite() {
//        if (userFavoritesRef != null) {
//            userFavoritesRef.child(url).setValue(true)
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(NewsDeatilActivity.this, "Marked as Favorite", Toast.LENGTH_SHORT).show();
//                        isFavorite = true;
//                        updateFavoriteIcon();
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(NewsDeatilActivity.this, "Failed to mark as favorite", Toast.LENGTH_SHORT).show());
//        }
//    }
//
//    private void unmarkAsFavorite() {
//        if (userFavoritesRef != null) {
//            userFavoritesRef.child(url).removeValue()
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(NewsDeatilActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
//                        isFavorite = false;
//                        updateFavoriteIcon();
//                    })
//                    .addOnFailureListener(e -> Toast.makeText(NewsDeatilActivity.this, "Failed to remove from favorites", Toast.LENGTH_SHORT).show());
//        }
//    }
//
//    private void updateFavoriteIcon() {
//        if (isFavorite) {
//            favoritesIV.setImageResource(R.drawable.favorite_filled);
//        } else {
//            favoritesIV.setImageResource(R.drawable.favorite_border);
//        }
//    }
//}



package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class NewsDeatilActivity extends AppCompatActivity {

    String title, desc, content, imageURL, url;
    private TextView titleTV, subDescTV, contentTV;
    private ImageView newsIV;
    private Button readNewsTV;

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
    }
}
