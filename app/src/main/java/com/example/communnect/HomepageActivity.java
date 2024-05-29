package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomepageActivity extends AppCompatActivity {
    Button paraadtosaprof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        paraadtosaprof=findViewById(R.id.btn_profile);
        if(paraadtosaprof==null){
            Toast.makeText(this, "heheheheehehehee", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, paraadtosaprof.getText(), Toast.LENGTH_SHORT).show();

        }

    }
}