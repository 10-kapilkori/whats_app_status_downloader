package com.whatsappstatusdownloader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button statusButton = findViewById(R.id.statusButton);
        Button savedStatusButton = findViewById(R.id.savedStatusButton);
        Button whatsAppGalleryButton = findViewById(R.id.whatsappGalleryButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().hide();

        statusButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, Status.class);
            startActivity(intent);
        });

        savedStatusButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, SavedStatus.class);
            startActivity(intent);
        });

        whatsAppGalleryButton.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, WhatsAppGallery.class);
            startActivity(intent);
        });
    }
}