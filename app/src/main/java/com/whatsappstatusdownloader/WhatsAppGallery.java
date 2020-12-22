package com.whatsappstatusdownloader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class WhatsAppGallery extends AppCompatActivity {
    private static final String TAG = "WhatsAppGallery";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_gallery);

        ListView listView = findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("WhatsApp Videos");
        arrayList.add("WhatsApp Images");
        arrayList.add("WhatsApp Docs");
        arrayList.add("WhatsApp Audio");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + Constant.MEDIA + Constant.WHATSAPP_VIDEO;
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + Constant.MEDIA + Constant.WHATSAPP_IMAGES;
        String docsPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + Constant.MEDIA + Constant.WHATSAPP_DOCUMENTS;
        String audioPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + Constant.MEDIA + Constant.WHATSAPP_AUDIO;

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                File file = new File(videoPath);
                boolean isCreated = file.exists();

                String[] files = file.list();
                if (isCreated) {
                    assert files != null;
                    if (files.length != 0 && files.length != 2) {
                        Log.i(TAG, "onCreate: path1 " + videoPath);
                        Log.i(TAG, "onCreate: " + Arrays.toString(files));
                        intent = new Intent(WhatsAppGallery.this, Gallery.class);
                        intent.putExtra("fileName", videoPath);
                        intent.putExtra("filesName", files);
                        startActivity(intent);
                    } else
                        Toast.makeText(this, "No Videos!", Toast.LENGTH_SHORT).show();
                }
            }
            if (position == 1) {
                File file = new File(imagePath);
                boolean isCreated = file.exists();

                String[] files = file.list();
                if (isCreated) {
                    assert files != null;
                    if (files.length != 0 && files.length != 2) {
                        intent = new Intent(WhatsAppGallery.this, Gallery.class);
                        Log.i(TAG, "onCreate: path1 " + imagePath);
                        Log.i(TAG, "onCreate: " + Arrays.toString(files));
                        intent.putExtra("fileName", imagePath);
                        intent.putExtra("filesName", files);
                        startActivity(intent);
                    } else
                        Toast.makeText(this, "No Photos!", Toast.LENGTH_SHORT).show();
                }
            }
            if (position == 2) {
                File file = new File(docsPath);
                boolean isCreated = file.exists();

                String[] files = file.list();
                if (isCreated) {
                    assert files != null;
                    if (files.length != 0 && files.length != 2) {
                        intent = new Intent(WhatsAppGallery.this, Gallery.class);
                        Log.i(TAG, "onCreate: " + Arrays.toString(files));
                        Log.i(TAG, "onCreate: path1 " + docsPath);
                        intent.putExtra("fileName", docsPath);
                        intent.putExtra("filesName", files);
                        startActivity(intent);
                    } else
                        Toast.makeText(this, "No Document files!", Toast.LENGTH_SHORT).show();
                }
            }
            if (position == 3) {
                File file = new File(audioPath);
                boolean isCreated = file.exists();

                String[] files = file.list();
                if (isCreated) {
                    assert files != null;
                    if (files.length != 0 && files.length != 2) {
                        intent = new Intent(WhatsAppGallery.this, Gallery.class);
                        Log.i(TAG, "onCreate: path1 " + audioPath);
                        Log.i(TAG, "onCreate: " + Arrays.toString(files));
                        intent.putExtra("fileName", audioPath);
                        intent.putExtra("filesName", files);
                        startActivity(intent);
                    } else
                        Toast.makeText(this, "No Audio files!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}













