package com.release.indeepen.management.networkManager.netArt;

import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.management.jsonManager.IndeepenJsonParser;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;

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
public class POSTImageContentRequest extends NetworkRequest<String> {

    @Override
    public URL getURL() throws MalformedURLException {
        return new URL("http://54.64.26.200/workPosts");
    }

    @Override
    public void setRequstMethod(HttpURLConnection conn) {
        setPost(conn);
    }

    @Override
    public void setData(Object data) {
        mData = (ContentData)data;
    }

    void setPost(HttpURLConnection conns){
        MultipartUtility multipart = null;
        try {

            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_POST, true);
            for(String path : ((ContentImageData)mData).arrIMGs) {
                multipart.addFilePart(DefineNetwork.POST_FILE, new File(path));
            }
            multipart.addFormField(DefineNetwork.POST_IMAGE_BLOG_ID, mData.sBlogKey);
            multipart.addFormField(DefineNetwork.POST_IMAGE_ARTTYPE, mData.nArtType+"");
            multipart.addFormField(DefineNetwork.POST_IMAGE_EMOTION, mData.nEmotion+"");
            multipart.addFormField(DefineNetwork.POST_IMAGE_TEXT, mData.sText);
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
