package com.example.blnsft.photos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blnsft.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photoList;

    PhotoAdapter() {
        photoList = new ArrayList<>();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_view_item , parent, false);
        return new PhotoViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.date.setText(photoList.get(position).getDate());
        holder.photo.setImageResource(photoList.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhotos(List<Photo> photos){
        photoList.addAll(photos);
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.viewItemPhoto)
        ImageView photo;
        @BindView(R.id.viewItemPhotoTime)
        TextView date;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
