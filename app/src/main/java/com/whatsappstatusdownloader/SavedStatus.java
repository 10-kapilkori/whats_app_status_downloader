package com.whatsappstatusdownloader;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SavedStatus extends AppCompatActivity {
    private static final String TAG = "SavedStatus";
    ArrayList<StoryModel> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_status);

        this.setTitle("Saved Status");

        ListView savedListView = findViewById(R.id.savedListView);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.SAVE_FOLDER_NAME;
        File file = new File(path);
        String[] files = file.list();
        Log.i(TAG, "onCreate: " + Arrays.toString(files));
        StoryModel model;

        if (files != null) {
            for (String s : files) {
                model = new StoryModel();
                String concat = path + s;
                model.setPath(concat);
                arrayList.add(model);
                Log.i(TAG, "onCreate: " + s);
                boolean isCreated = file.exists();
                Log.i(TAG, "onCreate: Path " + model.getPath());
                Log.i(TAG, "onCreate: List " + getList());
                if (isCreated) {
                    Log.i(TAG, "onCreate: hello");
                    SavedCustomAdapter savedCustomAdapter = new SavedCustomAdapter(this, R.layout.saved_status_gallery, files, path, getList());
                    savedListView.setAdapter(savedCustomAdapter);
                }
            }
        }
    }

    public ArrayList<StoryModel> getList() {
        Log.i(TAG, "getList: " + arrayList  );
        return arrayList;
    }
}