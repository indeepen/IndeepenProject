package com.release.indeepen.management.networkManager.netLogin;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netLogin.data.IndeepenLoginResult;
import com.release.indeepen.management.networkManager.netLogin.data.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-12.
 */
public class POSTLoginRequest extends NetworkRequest<IndeepenLoginResult> {
    String sURL;
    String sId;
    String sPassword;
    String sGCM;


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

    }

    public void setData(String val1, String val2) {
        sId = val1;
        sPassword = val2;
    }

    public void setData(String val1, String val2, String val3) {
        sId = val1;
        sPassword = val2;
        sGCM = val3;
    }

    void setPost(HttpURLConnection conns) {
        MultipartUtility multipart = null;
        try {

            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_POST, false);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", sId)
                    .appendQueryParameter("password", sPassword)
                    .appendQueryParameter("GCMKey", sGCM);

            String query = builder.build().getEncodedQuery();

            multipart.addPlaneTextValue(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IndeepenLoginResult parsing(InputStream is) {
        IndeepenLoginResult result = null;
        try {

            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            result = new Gson().fromJson(reader, IndeepenLoginResult.class);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }




   /* private JSONObject getJsonObject(InputStream is) {

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
    }*/
}