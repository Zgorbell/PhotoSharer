package com.example.blnsft.callbacks;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.blnsft.data.db.model.Photo;

public class PhotosDiffUtilItemCallback extends DiffUtil.ItemCallback<Photo>{
    private static final String TAG = PhotosDiffUtilItemCallback.class.getSimpleName();

    @Override
    public boolean areItemsTheSame(Photo oldItem, Photo newItem) {
//        Log.e(TAG,String.valueOf(oldItem.getId().equals(newItem.getId())));
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(Photo oldItem, Photo newItem) {
//        Log.e(TAG,  String.valueOf( oldItem.getPath().equals(newItem.getPath())
//                && oldItem.getDate().equals(newItem.getDate())));
        return oldItem.getPath().equals(newItem.getPath())
               && oldItem.getDate().equals(newItem.getDate());
    }
}
