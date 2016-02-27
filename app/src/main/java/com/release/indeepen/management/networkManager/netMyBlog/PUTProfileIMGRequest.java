package com.release.indeepen.management.networkManager.netMyBlog;

import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.management.jsonManager.IndeepenJsonParser;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.ChangeIMGData;

import org.json.JSONException;
import org.json.JSONObject;

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
public class PUTProfileIMGRequest extends NetworkRequest<String> {
    String sURL;
    ChangeIMGData mData;

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
        mData = (ChangeIMGData)data;
    }

    void setPost(HttpURLConnection conns){
        MultipartUtility multipart = null;
        try {
            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_PUT, true);

            multipart.addFormField(DefineNetwork.POST_BLOG_ID, mData.sChangeBlogKey);
            multipart.addFilePart(DefineNetwork.POST_FILE, new File(mData.sChangePath));
            multipart.finish();

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
            return  "";
        }



}
