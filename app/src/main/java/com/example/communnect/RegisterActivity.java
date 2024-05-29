package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    CheckBox cbShowHidePass;
    Button btnSignUp;
    EditText editPass, editConfirmPass;
    EditText mEmailEt, mPasswordEt, mFirstNameEt, mLastNameEt;

    FirebaseDatabase database;
    DatabaseReference reference;
//    TextView mloginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mFirstNameEt = findViewById(R.id.editTextFname); // Firstname edit text
        mLastNameEt = findViewById(R.id.editTextLname);   // Lastname edit text
        mEmailEt = findViewById(R.id.editTextEmail); // email edit text
        mPasswordEt = findViewById(R.id.editTextPassword); // Initialize mPasswordEt here
        editConfirmPass = findViewById(R.id.editTextConfirmPassword); //confirm password edit text
        cbShowHidePass = findViewById(R.id.cbShowHidePassword); // show hide password checkbox
        btnSignUp = findViewById(R.id.btnSignUp); // sign up button

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        cbShowHidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPasswordEt.setTransformationMethod(null);
                    editConfirmPass.setTransformationMethod(null);
                } else {
                    mPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
                    editConfirmPass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String email = mEmailEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        String confirmPassword = editConfirmPass.getText().toString().trim();
        String firstName = mFirstNameEt.getText().toString().trim();
        String lastName = mLastNameEt.getText().toString().trim();

        if (email.isEmpty()) {
            mEmailEt.setError("Email is required");
            mEmailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEt.setError("Invalid email format");
            mEmailEt.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mPasswordEt.setError("Password is required");
            mPasswordEt.requestFocus();
            return;
        }

        if (password.length() < 6) {
            mPasswordEt.setError("Password length should be at least 6 characters");
            mPasswordEt.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            editConfirmPass.setError("Passwords do not match");
            editConfirmPass.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            mFirstNameEt.setError("First name is required");
            mFirstNameEt.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            mLastNameEt.setError("Last name is required");
            mLastNameEt.requestFocus();
            return;
        }

        String userId = reference.push().getKey();
        HelperClass helperClass = new HelperClass(firstName, lastName, email, password);
        reference.child(userId).setValue(helperClass, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(RegisterActivity.this, "Data could not be saved " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
            }
        });
        MainActivity.UserId = firstName;

    }

}
