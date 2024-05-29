package com.example.communnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";
    Button addProf, updateData;
    ImageView profile;
    EditText firstname, lastname;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_edit_profile);

        addProf = findViewById(R.id.btnImagePicker);
        profile = findViewById(R.id.imageProfile);
        firstname = findViewById(R.id.editTextFirstname);
        lastname = findViewById(R.id.editTextLastName);
        updateData = findViewById(R.id.btnsaveeditprof);

        if (addProf == null || profile == null) {
            Log.e(TAG, "onCreate: Button or ImageView is null. Check the layout IDs.");
            return;
        }

        // Retrieve userId from the Intent
        userId = getIntent().getStringExtra("userId");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfile.this, "Button clicked", Toast.LENGTH_SHORT).show();
                ImagePicker.with(EditProfile.this)
                        .crop() // Crop image(Optional), Check Customization for more option
                        .compress(1024) // Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstname.getText().toString().trim();
                String lastName = lastname.getText().toString().trim();

                if (firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(EditProfile.this, "First name and Last name are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                fetchUserDataAndUpdate(userId, firstName, lastName);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                profile.setImageURI(uri);
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Image selection cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserDataAndUpdate(String userId, String firstName, String lastName) {
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HelperClass currentUser = snapshot.getValue(HelperClass.class);
                if (currentUser != null) {
                    // Update only first name and last name
                    currentUser.setFirstname(firstName);
                    currentUser.setLastname(lastName);
                    updateDataInDatabase(userId, currentUser);
                } else {
                    Toast.makeText(EditProfile.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfile.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDataInDatabase(String userId, HelperClass updatedUser) {
        reference.child(userId).setValue(updatedUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(EditProfile.this, "Data could not be updated " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
