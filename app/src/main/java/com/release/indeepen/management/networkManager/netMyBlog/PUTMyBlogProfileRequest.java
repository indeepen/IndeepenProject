package com.release.indeepen.management.networkManager.netMyBlog;

import android.net.Uri;

import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.management.jsonManager.IndeepenJsonParser;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.ChangeIMGData;
import com.release.indeepen.user.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-12.
 */
public class PUTMyBlogProfileRequest extends NetworkRequest<String> {
    String sURL;
    UserData mData;

    public URL getURL() throws MalformedURLException {
        return new URL(sURL);
    }

    public void setURL(String url) {
        sURL = url;
    }

    @Override
    public void setRequstMethod(HttpURLConnection conn) {
        setPost(conn);
    }

    @Override
    public void setData(Object data) {
        mData = (UserData) data;
    }

    void setPost(HttpURLConnection conns) {
        MultipartUtility multipart = null;
        try {
            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_PUT, false);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("nick", mData.sArtist)
                    .appendQueryParameter("intro",  mData.sIntrodution)
                    .appendQueryParameter("name",  mData.sName)
                    .appendQueryParameter("phone",  mData.sPhoneNum)
                    .appendQueryParameter("email", mData.sEmail)
                    .appendQueryParameter("isPublic", mData.isPublic ? "true" : "false");
            String query = builder.build().getEncodedQuery();

            multipart.addPlaneTextValue(query);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String parsing(InputStream is) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
            JSONObject jsonObject = IndeepenJsonParser.getInstance().getJsonObject(is);
            return jsonObject.getString("msg");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


}
