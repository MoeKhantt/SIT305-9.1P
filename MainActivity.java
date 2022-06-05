package com.example.task_71p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button showButton, createButton,showMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createButton = findViewById(R.id.createButton);
        showButton = findViewById(R.id.showButton);
        showMapButton = findViewById(R.id.showMapButton);

        Intent ShowAdverts = new Intent(this, ShowAdvert.class);
        Intent CreateAdverts= new Intent(this, CreateAdvert.class);
        Intent ShowMap= new Intent(this, ShowOnMap.class);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CreateAdverts);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShowAdverts);
            }
        });

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShowMap);
            }
        });
    }
}