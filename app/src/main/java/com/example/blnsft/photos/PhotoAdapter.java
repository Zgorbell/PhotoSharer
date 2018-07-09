package com.example.blnsft.photos;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blnsft.R;
import com.example.blnsft.models.Photo;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photoList;
    private int displayWidth;

    PhotoAdapter() {
        photoList = new ArrayList<>();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_view_item , parent, false);
        displayWidth = itemLayoutView.getContext().getResources().getDisplayMetrics().widthPixels;
        itemLayoutView.getLayoutParams().width = displayWidth / 3;
        itemLayoutView.getLayoutParams().height = displayWidth / 3 + displayWidth / 15;
        return new PhotoViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.get().load(photoList.get(position).getUrl())
                .resize(displayWidth / 3, displayWidth / 3)
                .into(holder.photoView);
        long time = Long.valueOf(photoList.get(position).getDate()) * 1000;
        holder.date.setText(getDate(time,DateFormat.MEDIUM));
        holder.id = photoList.get(position).getId();
        holder.lat = photoList.get(position).getLat();
        holder.lng = photoList.get(position).getLng();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhotos(List<Photo> photos){
        photoList.addAll(photos);
    }

    public void deleteAll(){
        photoList.clear();
    }

    public static String getDate(long milliSeconds, int dateFormat)
    {
        DateFormat formatter = DateFormat.getDateInstance(dateFormat);
        return formatter.format(new Date(milliSeconds));
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener{
        @BindView(R.id.viewItemPhoto)
        ImageView photoView;
        @BindView(R.id.viewItemPhotoTime)
        TextView date;
        String id;
        String lat;
        String lng;
        PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Log.e("Photo context menu", "Photo context menu started");
            Log.e("Photo context menu", "Pressed position " + getAdapterPosition() + " and id" + id);
            menu.setHeaderTitle("Select The Action");
            menu.add(Integer.valueOf(id), v.getId(), 0, "Delete");
        }
    }
}
