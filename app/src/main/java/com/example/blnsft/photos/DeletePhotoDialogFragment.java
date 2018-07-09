package com.example.blnsft.photos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.blnsft.R;
import com.example.blnsft.presenters.DeletePhotoPresenter;
import com.example.blnsft.views.DeletePhotoView;

public class DeletePhotoDialogFragment extends MvpAppCompatDialogFragment implements
             DeletePhotoView, DialogInterface.OnClickListener {
    public static final String PHOTO_ID = "photo id";
    public static final String DELETE_PHOTO_DIALOG_TAG = "delete photo dialog tag";
    @InjectPresenter
    DeletePhotoPresenter deletePhotoPresenter;

    public static DeletePhotoDialogFragment newInstance(String token, String id) {
        Bundle args = new Bundle();
        args.putString(PhotosFragment.TOKEN, token);
        args.putString(PHOTO_ID, id);
        DeletePhotoDialogFragment fragment = new DeletePhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private String token;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getArguments().getString(PhotosFragment.TOKEN);
        id = getArguments().getString(PHOTO_ID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e("DeletePhotoDialog", "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(R.string.dialog_deletePhoto_message)
                .setPositiveButton("Yes", this)
                .setNegativeButton("Cancel", this).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                Log.e("DeletePhotoDialog", "Pressed button yes");
                deletePhotoPresenter.deletePhoto(token, id);
                break;
            case DialogInterface.BUTTON_NEGATIVE:

        }
    }
}
