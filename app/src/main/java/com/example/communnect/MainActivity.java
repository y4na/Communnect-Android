package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static String UserId;
    //para pass sa value to read

    Button btnCreateAccount;
    TextView btnSignIn;
    // public static String full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        btnCreateAccount = findViewById(R.id.mainBtnEmail);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        btnSignIn = findViewById(R.id.mainBtnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });


        //Terms of Service & Privacy Policy
        TextView textAgreements = findViewById(R.id.textAgreements);

        SpannableString spannableString = new SpannableString(textAgreements.getText());

        int startIndexTerms = textAgreements.getText().toString().indexOf("Terms of Service");
        int endIndexTerms = startIndexTerms + "Terms of Service".length();

        int startIndexPrivacy = textAgreements.getText().toString().indexOf("Privacy Policy");
        int endIndexPrivacy = startIndexPrivacy + "Privacy Policy".length();

        ClickableSpan clickableSpanTerms = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Handle click action for Terms of Service
                // Example: Open Terms of Service activity or web page
            }
        };
        spannableString.setSpan(clickableSpanTerms, startIndexTerms, endIndexTerms, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpanPrivacy = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // Handle click action for Privacy Policy
                // Example: Open Privacy Policy activity or web page
            }
        };
        spannableString.setSpan(clickableSpanPrivacy, startIndexPrivacy, endIndexPrivacy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new UnderlineSpan(), startIndexTerms, endIndexTerms, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), startIndexPrivacy, endIndexPrivacy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textAgreements.setText(spannableString);

        textAgreements.setMovementMethod(LinkMovementMethod.getInstance());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}