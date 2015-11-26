package com.release.indeepen.management.networkManager;

import com.release.indeepen.content.ContentData;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-10.
 */
public abstract  class NetworkRequest<T> {
    public abstract URL getURL() throws MalformedURLException;
    public abstract T parsing(InputStream is);
    public abstract void setRequstMethod(HttpURLConnection conn);
    public abstract void setData(Object data);

    public ContentData mData;
    public HttpURLConnection myConn;
    private boolean isCancel = false;
    public void cancel() {
        isCancel = true;
    }
    public boolean isCancel() {
        return isCancel;
    }
}
