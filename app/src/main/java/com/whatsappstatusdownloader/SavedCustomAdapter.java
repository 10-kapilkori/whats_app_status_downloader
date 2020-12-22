package com.whatsappstatusdownloader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class SavedCustomAdapter extends ArrayAdapter<String> {
    private static final String TAG = "SavedCustomAdapter";
    String[] files;
    String mPath;
    List<StoryModel> savedStatus;

    public SavedCustomAdapter(@NonNull Context context, int resource, String[] files, String path, List<StoryModel> model) {
        super(context, resource);
        this.files = files;
        this.mPath = path;
        this.savedStatus = model;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.saved_status_gallery, null, false);
        ImageView savedImageView = view.findViewById(R.id.savedImageView);
        ImageView savedImageButton = view.findViewById(R.id.savedImagePlayButton);

        StoryModel model = savedStatus.get(position);
        Log.i(TAG, "getView: " + model.getPath());
        if (model.getPath().endsWith(".mp4")) {
            savedImageButton.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(model.getPath())
                    .into(savedImageView);
        } else {
            savedImageButton.setVisibility(View.INVISIBLE);
            savedImageView.setImageBitmap(BitmapFactory.decodeFile(model.getPath()));
        }
        return view;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + files.length);
        return files.length;
    }
}