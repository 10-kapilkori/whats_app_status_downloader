package com.whatsappstatusdownloader;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class
VideoCustomRecyclerViewAdapter extends RecyclerView.Adapter<VideoCustomRecyclerViewAdapter.VideoRecyclerViewHolder> {
    private static final String TAG = "VideoCustomRecyclerView";

    private final Context context;
    private final ArrayList<Object> filesList;

    public class VideoRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImageView;
        ImageView imagePlayButton;
        ImageView savedID;

        public VideoRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            videoImageView = itemView.findViewById(R.id.videoImageView);
            imagePlayButton = itemView.findViewById(R.id.imagePlayButton);
            savedID = itemView.findViewById(R.id.downloadID);
        }
    }

    public VideoCustomRecyclerViewAdapter(Context context, ArrayList<Object> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public VideoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_detail, null, false);

        return new VideoRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewHolder holder, int position) {
        StoryModel model = (StoryModel) filesList.get(position);

        if (model.getUri().toString().endsWith(".mp4")) {
            holder.imagePlayButton.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(model.getUri())
                    .into(holder.videoImageView);
        }

        holder.savedID.setOnClickListener(v -> {
            statusFolder();

            final String path = ((StoryModel) filesList.get(position)).getPath();
            final File file = new File(path);
            String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.SAVE_FOLDER_NAME;
            File destFile = new File(destinationPath);

            try {
                FileUtils.copyFileToDirectory(file, destFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            MediaScannerConnection.scanFile(
                    context,
                    new String[] {
                            destinationPath + model.getFileName()
                    },
                    new String[] { " */* " },

                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }

                        @Override
                        public void onMediaScannerConnected() {

                        }
                    }
            );
            Toast.makeText(context, "Saved to: " + destinationPath + model.getFileName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void statusFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.SAVE_FOLDER_NAME;
        File dir = new File(path);
        boolean isCreated = dir.exists();

        if (!isCreated) {
            isCreated = dir.mkdir();
        } else {
            Log.i(TAG, "statusFolder: already created");
        }
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }
}
