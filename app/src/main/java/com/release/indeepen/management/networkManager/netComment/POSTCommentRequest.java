package com.release.indeepen.management.networkManager.netComment;

import android.net.Uri;

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
public class POSTCommentRequest extends NetworkRequest<String> {
    String sURL;
    String sContent;
    String sBlogKey;

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
        sContent = (String) data;
    }

    public void setData(Object data, String blogkey) {
        sContent = (String) data;
        sBlogKey = blogkey;
    }

    void setPost(HttpURLConnection conns) {
        MultipartUtility multipart = null;
        try {

            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_POST, false);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("content", sContent)
                    .appendQueryParameter("writer",  sBlogKey);

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


    private JSONObject getJsonObject(InputStream is) {

        //Create input stream
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            //returns the json object
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if something went wrong, return null
        return null;
    }

}
