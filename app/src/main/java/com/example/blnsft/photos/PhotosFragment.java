package com.example.blnsft.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.blnsft.R;
import com.example.blnsft.models.Photo;
import com.example.blnsft.presenters.DownLoadPhotoPresenter;
import com.example.blnsft.views.DownloadPhotosView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PhotosFragment extends MvpAppCompatFragment implements
        DownloadPhotosView {
    public static final String TOKEN = "token";
    @InjectPresenter
    DownLoadPhotoPresenter downLoadPhotoPresenter;
    @BindView(R.id.recyclerViewPhotos)
    RecyclerView recyclerViewPhotos;
    Unbinder unbinder;
    private PhotoAdapter photoAdapter;
    private String token;

    public static PhotosFragment newInstance(String token) {
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        PhotosFragment fragment = new PhotosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getArguments().getString(TOKEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("FragmentPhotos", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerViewPhotos.setLayoutManager(gridLayoutManager);
        photoAdapter = new PhotoAdapter();
        recyclerViewPhotos.setAdapter(photoAdapter);
        recyclerViewPhotos.addOnScrollListener(new PhotosOnScrollListener());
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void addPhotos(List<Photo> photoList) {
        photoAdapter.addPhotos(photoList);
        photoAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        showDeleteDialog(token, String.valueOf(item.getGroupId()));
        return true;
    }

    public void showDeleteDialog(String token, String id) {
        DeletePhotoDialogFragment dialogFragment = DeletePhotoDialogFragment.newInstance(token, id);
        dialogFragment.show(getFragmentManager(), DeletePhotoDialogFragment.DELETE_PHOTO_DIALOG_TAG);
    }

    public class PhotosOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
