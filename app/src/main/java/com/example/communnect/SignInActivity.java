package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    TextView btnForgotPass;
    CheckBox cbShowHidePass;
    Button btnCreateNewAcc;
    Button btnSignIn;
    EditText signInEmail, signInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);


        btnSignIn = findViewById(R.id.btnSignIn);
        cbShowHidePass = findViewById(R.id.cbShowHidePassword);
        btnCreateNewAcc = findViewById(R.id.btnCreateNewAcc);
        btnForgotPass = findViewById(R.id.btnForgotPassword);
        signInEmail = findViewById(R.id.editTextEmail);
        signInPassword = findViewById(R.id.editTextPassword);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){
                    System.out.println("Username and password invalid");
                } else {
                    checkUser();
                }
            }
        });

        btnCreateNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
                finish();
            }
        });

        cbShowHidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    signInPassword.setTransformationMethod(null);
                } else {
                    signInPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public Boolean validateUsername(){
        String val = signInEmail.getText().toString();
        if (val.isEmpty()){
            signInEmail.setError("Email cannot be empty");
            return false;
        } else {
            signInEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = signInPassword.getText().toString();
        if (val.isEmpty()){
            signInPassword.setError("Password cannot be empty");
            return false;
        } else {
            signInPassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userEmail = signInEmail.getText().toString().trim();
        String userPassword = signInPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);
       // reference.orderByChild("email").get();
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)){
                            //Valid credentials
                            String nameFromDB = userSnapshot.child("name").getValue(String.class);
                            String emailFromDB = userSnapshot.child("email").getValue(String.class);
                            String lastnameFromDB = userSnapshot.child("lastname").getValue(String.class);

                            // Proceed with login
                            Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("lastname", lastnameFromDB);
                            intent.putExtra("password", passwordFromDB);
                            startActivity(intent);
                            MainActivity.UserId=nameFromDB;
                            //pass para sa read sa profle
                            finish(); // Finish the LoginActivity to prevent going back
                        } else {
                            signInPassword.setError("Invalid Credentials");
                            signInPassword.requestFocus();
                        }
                    }
                } else {
                    signInEmail.setError("Email does not exist");
                    signInEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(SignInActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}