package com.example.blnsft.photos;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blnsft.R;
import com.example.blnsft.models.Photo;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        Picasso.get().load(photoList.get(position).getUrl()).into(holder.photoView);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG);
        holder.date.setText(photoList.get(position).getDate());
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
            menu.setHeaderTitle("Select The Action");
            menu.add(getAdapterPosition(), v.getId(), 0, "Delete");//groupId, itemId, order, title
        }
    }
}
