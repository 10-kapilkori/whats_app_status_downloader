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

import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class PhotoCustomRecyclerViewAdapter extends RecyclerView.Adapter<PhotoCustomRecyclerViewAdapter.PhotoRecyclerViewHolder> {
    private static final String TAG = "PhotoCustomRecyclerView";

    private final Context context;
    private final ArrayList<Object> filesList;

    public class PhotoRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        ImageView savedId;

        public PhotoRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            savedId = itemView.findViewById(R.id.downloadImageID);
        }
    }

    public PhotoCustomRecyclerViewAdapter(Context context, ArrayList<Object> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    @NonNull
    @Override
    public PhotoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_details, null, false);
        PhotoRecyclerViewHolder viewHolder = new PhotoRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoRecyclerViewHolder holder, int position) {
        StoryModel model = (StoryModel) filesList.get(position);

        if (!model.getUri().toString().endsWith(".mp4")) {
            holder.photoImageView.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(model.getUri())
                    .into(holder.photoImageView);
        }

        holder.savedId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
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
