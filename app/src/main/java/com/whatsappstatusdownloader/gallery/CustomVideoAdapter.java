package com.whatsappstatusdownloader.gallery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.whatsappstatusdownloader.R;
import com.whatsappstatusdownloader.StoryModel;

import java.util.List;

public class CustomVideoAdapter extends ArrayAdapter<String> {
    private static final String TAG = "CustomVideoAdapter";

    Context mContext;
    List<StoryModel> mList;
    String[] mFiles;
    int mResource;

    public CustomVideoAdapter(@NonNull Context context, int resource, List<StoryModel> list, String[] files) {
        super(context, R.layout.whatsapp_gallery_video, files);
        this.mContext = context;
        this.mResource = resource;
        this.mFiles = files;
        this.mList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.whatsapp_gallery_video, null, false);
        ImageView imageView = view.findViewById(R.id.videoGalleryImageView);
        ImageView playButton = view.findViewById(R.id.videoImagePlayButton);

        playButton.setVisibility(View.VISIBLE);

        Log.i(TAG, "getView: video " + mList.toString());
        StoryModel video = mList.get(position);
        Log.i(TAG, "getView: video " + video.getPath());
        Glide.with(getContext())
                .load(video.getPath())
                .into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + mFiles.length);
        return mFiles.length;
    }
}