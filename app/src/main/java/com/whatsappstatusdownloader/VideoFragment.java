package com.whatsappstatusdownloader;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private static final String TAG = "VideoFragment";

    private RecyclerView videoRecyclerView;
    File[] files;
    private final ArrayList<Object> filesList = new ArrayList<>();

    public VideoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoRecyclerView = view.findViewById(R.id.videoListView);
        Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

        return view;
    }

    private void setRefreshes() {
        filesList.clear();
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VideoCustomRecyclerViewAdapter videoAdapter = new VideoCustomRecyclerViewAdapter(getContext(), getVideoData());
        videoRecyclerView.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
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

            if (!storyModel.getUri().toString().equals(".nomedia") && storyModel.getUri().toString().endsWith(".mp4")) {
                filesList.add(storyModel);
            }
        }
        return filesList;
    }

}