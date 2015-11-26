package com.release.indeepen.management.networkManager.netCommon;

import android.net.Uri;

import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.management.jsonManager.IndeepenJsonParser;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.user.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-12.
 */
public class PUTLikeRequest extends NetworkRequest<String> {
    String sURL;


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
    public void setData(Object data){
    }

    void setPost(HttpURLConnection conns) {

        try {
            conns.setRequestMethod(DefineNetwork.METHOD_PUT);
        } catch (ProtocolException e) {
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
