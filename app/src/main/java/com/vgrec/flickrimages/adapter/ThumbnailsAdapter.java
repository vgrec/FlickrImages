package com.vgrec.flickrimages.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vgrec.flickrimages.Constants;
import com.vgrec.flickrimages.R;
import com.vgrec.flickrimages.SizeSuffix;
import com.vgrec.flickrimages.VolleySingleton;
import com.vgrec.flickrimages.model.photo.Photo;

import java.util.List;


public class ThumbnailsAdapter extends BaseAdapter {
    private final ImageLoader imageLoader;
    private LayoutInflater inflater;
    private List<Photo> photos;

    public ThumbnailsAdapter(Context context, List<Photo> photos) {
        inflater = LayoutInflater.from(context);
        imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_grid_view, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Photo photo = photos.get(position);
        String url = String.format(Constants.IMAGE_URL, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret(), SizeSuffix.m);
        holder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.thumbnail.setImageUrl(url, imageLoader);

        return view;
    }

    private static class ViewHolder {
        NetworkImageView thumbnail;
    }
}
