package com.whatsappstatusdownloader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsappstatusdownloader.gallery.CustomImageAdapter;
import com.whatsappstatusdownloader.gallery.CustomVideoAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Gallery extends AppCompatActivity {
    private static final String TAG = "Gallery";

    ArrayList<StoryModel> arrayList = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image);

        ListView listView = findViewById(R.id.galleryListView);
        Intent intent = getIntent();

        String file = intent.getStringExtra("fileName");
        String[] files = intent.getStringArrayExtra("filesName");

        Log.i(TAG, "onCreate: " + files.length);
        String[] mode = new String[files.length - 2];

        for (String s : files) {
            StoryModel model = new StoryModel();
            String concat = file + "/" + s;
            model.setPath(concat);

            if (s.endsWith(".jpg") || s.endsWith("jpeg")) {
                for (int i=0; i<mode.length; i++) {
                    mode[i] = s;
                }
                Log.i(TAG, "onCreate: " + Arrays.toString(mode));
                Log.i(TAG, "onCreate: " + mode.length);
                arrayList.add(model);
                CustomImageAdapter galleryAdapter = new CustomImageAdapter(this, R.layout.whatsapp_gallery_image, getList(), mode);
                listView.setAdapter(galleryAdapter);
            } else if (s.endsWith(".mp4")) {
                for (int i=0 ; i<mode.length; i++) {
                    mode[i] = s;
                }
                Log.i(TAG, "onCreate: " + Arrays.toString(mode));
                Log.i(TAG, "onCreate: " + mode.length);
                arrayList.add(model);
                CustomVideoAdapter videoAdapter = new CustomVideoAdapter(this, R.layout.whatsapp_gallery_video, getList(), mode);
                listView.setAdapter(videoAdapter);

            } else if (s.endsWith(".docx") || s.endsWith(".pdf") || s.endsWith(".xls") || s.endsWith(".xlsx")) {
                ArrayList<String> docsArrayList = new ArrayList<>();
                for (String splitFiles : files) {
                    if (!splitFiles.endsWith("Private") && !splitFiles.endsWith("Sent")) {
                        docsArrayList.add(splitFiles);
                    }
                    ArrayAdapter<String> docsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, docsArrayList);
                    listView.setAdapter(docsAdapter);
                }
            } else if (s.endsWith(".mp3")) {
                ArrayList<String> audioArrayList = new ArrayList<>(Arrays.asList(files));
                for (String splitFiles : files) {
                    if (!splitFiles.endsWith("Private") && !splitFiles.endsWith("Sent")) {
                        audioArrayList.add(splitFiles);
                    }
                    ArrayAdapter<String> audioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, audioArrayList);
                    listView.setAdapter(audioAdapter);
                }
            }
        }
    }

    private ArrayList<StoryModel> getList() {
        Log.i(TAG, "getList: " + arrayList.size());
        return arrayList;
    }
}