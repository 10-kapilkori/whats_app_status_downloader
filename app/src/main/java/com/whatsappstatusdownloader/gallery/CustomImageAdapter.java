package com.whatsappstatusdownloader.gallery;

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

import com.whatsappstatusdownloader.R;
import com.whatsappstatusdownloader.StoryModel;

import java.util.List;

public class CustomImageAdapter extends ArrayAdapter<String> {
    private static final String TAG = "CustomAdapter";

    Context mContext;
    List<StoryModel> mList;
    String[] mFiles;
    int mResource;

    public CustomImageAdapter(@NonNull Context context, int resource, List<StoryModel> list, String[] files) {
        super(context, R.layout.whatsapp_gallery_image, files);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
        this.mFiles = files;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.whatsapp_gallery_image, null, false);
        ImageView imageView = view.findViewById(R.id.galleryImageView);

        Log.i(TAG, "getView: " + mFiles.length);

        StoryModel image = mList.get(position);
        imageView.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));

        return view;
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + mFiles.length);
        return mFiles.length;
    }
}