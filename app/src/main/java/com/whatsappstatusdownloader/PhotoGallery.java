package com.whatsappstatusdownloader;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoGallery extends AppCompatActivity {
    private static final String TAG = "PhotoGallery";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    File[] files;
    private final ArrayList<Object> filesList = new ArrayList<>();
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        initView();

        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        setRefreshes();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        setRefreshes();
    }

    private void setRefreshes() {
        filesList.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        intent = getIntent();

        int photo = intent.getIntExtra("photo", -1);
        int video = intent.getIntExtra("video", -1);

        if (photo == 0) {
            PhotoCustomRecyclerViewAdapter photoAdapter = new PhotoCustomRecyclerViewAdapter(this, getPhotoData());
            Log.i(TAG, "setRefreshes: photo " + photo);
            recyclerView.setAdapter(photoAdapter);
            photoAdapter.notifyDataSetChanged();
        } else if (video == 1) {
            VideoCustomRecyclerViewAdapter videoAdapter = new VideoCustomRecyclerViewAdapter(this, getVideoData());
            Log.i(TAG, "setRefreshes: video " + video);
            recyclerView.setAdapter(videoAdapter);
            videoAdapter.notifyDataSetChanged();
        }
    }

    private ArrayList<Object> getPhotoData() {
        StoryModel storyModel;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + "Media/.Statuses";

        File transferDirector = new File(targetPath);

        files = transferDirector.listFiles();

        for (File file : files) {
            storyModel = new StoryModel();
            storyModel.setUri(Uri.fromFile(file));
            storyModel.setPath(file.getAbsolutePath());
            storyModel.setFileName(file.getName());

            if (!storyModel.getUri().toString().equals(".nomedia"))
                filesList.add(storyModel);
        }
        return filesList;
    }

    private ArrayList<Object> getVideoData() {
        StoryModel storyModel;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FOLDER_NAME + "Media/.Statuses";

        File targetDirectory = new File(targetPath);

        files = targetDirectory.listFiles();

        for (File file : files) {
            storyModel = new StoryModel();
            storyModel.setUri(Uri.fromFile(file));
            storyModel.setPath(file.getAbsolutePath());
            storyModel.setFileName(file.getName());

            if (!storyModel.getUri().toString().equals(".nomedia")) {
                filesList.add(storyModel);
            }
        }
        return filesList;
    }
}