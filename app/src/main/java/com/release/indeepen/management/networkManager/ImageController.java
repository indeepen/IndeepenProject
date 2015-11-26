package com.release.indeepen.management.networkManager;

import android.graphics.Bitmap;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lyo on 2015-11-10.
 */
public class ImageController {

    private static ImageController instance;
    private ThreadPoolExecutor mExecutor;
    public static ImageController getInstance() {
        if (instance == null) {
            instance = new ImageController();
        }
        return instance;
    }

    private ImageController(){
        mExecutor = NetworkManager.getInstance().getExecutor();
    }

    public void getImageBitmap(NetworkRequest<Bitmap> request, NetworkProcess.OnResultListener<Bitmap> listener) {
        NetworkManager.getInstance().getExecutor().execute(new NetworkProcess<Bitmap>(request, listener));
    }

}
