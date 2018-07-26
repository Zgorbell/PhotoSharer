package com.example.blnsft.callbacks;

import android.arch.paging.PagedList;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.blnsft.data.db.model.Photo;

import java.util.List;

class PhotosDiffUtilCallback extends DiffUtil.Callback{
    private static final String TAG = PhotosDiffUtilItemCallback.class.getSimpleName();
    private final List<Photo> oldList;
    private final List<Photo> newList;

    public PhotosDiffUtilCallback(PagedList<Photo> oldList, PagedList<Photo> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Photo oldItem = oldList.get(oldItemPosition);
        Photo newItem = newList.get(newItemPosition);
        Log.e(TAG,String.valueOf(oldItem.getId().equals(newItem.getId())));
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Photo oldItem = oldList.get(oldItemPosition);
        Photo newItem = newList.get(newItemPosition);
        Log.e(TAG,  String.valueOf( oldItem.getPath().equals(newItem.getPath())
                && oldItem.getDate().equals(newItem.getDate())));
        return oldItem.getPath().equals(newItem.getPath())
                && oldItem.getDate().equals(newItem.getDate());
    }
}

