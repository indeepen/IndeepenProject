package com.release.indeepen;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;


/**
 * Created by lyo on 2015-11-02.
 */
public class SharedApplication extends Application {

    private static Context mContext;


    public static Context getContext() {
        return mContext;
    }

  /*  public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(options)
        //.imageDownloader(new HttpClientImageDownloader(context, NetworkManager.getInstance().getExecutor()))
                .build();
        ImageLoader.getInstance().init(config);
    }*/

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
       /* initImageLoader(this);*/
        FacebookSdk.sdkInitialize(this);
        mContext = this;
/*
        FontClass.setDefaultFont(this, "DEFAULT", "NotoSansCJKkr-Regular.otf");
        FontClass.setDefaultFont(this, "MONOSPACE", "NotoSansCJKkr-Regular.otf");
        FontClass.setDefaultFont(this, "SERIF", "NotoSansCJKkr-Medium.otf");
        FontClass.setDefaultFont(this, "SANS_SERIF", "NotoSansCJKkr-Light.otf");
    */}
}
