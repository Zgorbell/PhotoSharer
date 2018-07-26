package com.example.blnsft.callbacks;

import android.util.Log;


import com.example.blnsft.App;
import com.example.blnsft.data.db.model.Photo;
import com.example.blnsft.data.network.models.PhotoResponseModel;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PhotoResponseAdapter {
    private static final String TAG = PhotoResponseAdapter.class.getSimpleName();
//    public static final String PHOTO_CACHE_DIR = "photos";

    public Photo adapt(PhotoResponseModel photoResponseModel, int page){
        return new Photo(photoResponseModel.getId(),
                getImagePath(photoResponseModel.getUrl(), photoResponseModel.getId()),
                photoResponseModel.getDate(),
                photoResponseModel.getLat(),
                photoResponseModel.getLng(),
                page);
    }

    public List<Photo> adapt(List<PhotoResponseModel> photoResponseModels, int page){
        List<Photo> photos = new ArrayList<>();
        for(PhotoResponseModel tempModel: photoResponseModels){
            photos.add(adapt(tempModel, page));
        }
        return photos;
    }

    private String getImagePath(String url, String name){
        return saveImage(downloadBitmap(url), name + System.currentTimeMillis()).getAbsolutePath();
    }

    private File saveImage(byte[] image, String name){
        File filesDir = App.getInstance().getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            os.write(image);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(TAG, "Error writing bitmap", e);
        }
        return imageFile;
    }

    private byte[] downloadBitmap(String urlImage) {
        try {
            URL url = new URL(urlImage);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                is = url.openStream ();
                byte[] byteChunk = new byte[4096];
                int n;
                while ( (n = is.read(byteChunk)) > 0 ) {
                    baos.write(byteChunk, 0, n);
                }
                return baos.toByteArray();
            }
            catch (IOException e) {
                System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
                e.printStackTrace ();
                return null;
            }
            finally {
                if (is != null) { is.close(); }
            }
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

}
