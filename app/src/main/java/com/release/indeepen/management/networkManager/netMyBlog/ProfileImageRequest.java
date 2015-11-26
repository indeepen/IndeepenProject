package com.release.indeepen.management.networkManager.netMyBlog;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoResult;
import com.release.indeepen.management.networkManager.netMyBlog.data.ImageResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-20.
 */
public class ProfileImageRequest extends NetworkRequest<ImageResult> {
    String sURL;

    @Override
    public URL getURL() throws MalformedURLException {
        return new URL(sURL);
    }

    public void setURL(String url) {
        sURL = url;
    }

    public void setRequstMethod(HttpURLConnection conn) {
        try {
            conn.setRequestMethod(DefineNetwork.METHOD_GET);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public ImageResult parsing(InputStream is) {
        ImageResult result = null;

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            result = new Gson().fromJson(reader, ImageResult.class);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}