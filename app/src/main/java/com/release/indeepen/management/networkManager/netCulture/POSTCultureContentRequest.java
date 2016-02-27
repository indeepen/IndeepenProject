package com.release.indeepen.management.networkManager.netCulture;

import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.management.jsonManager.IndeepenJsonParser;
import com.release.indeepen.management.networkManager.MultipartUtility;
import com.release.indeepen.management.networkManager.NetworkRequest;

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
public class POSTCultureContentRequest extends NetworkRequest<String> {


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
    public void setData(Object data) {
        mData = (ContentData)data;
    }

    void setPost(HttpURLConnection conns){
        MultipartUtility multipart = null;
        try {

            multipart = new MultipartUtility(conns, DefineNetwork.METHOD_POST, true);
            for(String path : ((CultureItemData)mData).arrIMGs) {
                multipart.addFilePart(DefineNetwork.POST_FILE, new File(path));
            }
            multipart.addFormField(DefineNetwork.POST_BLOG_ID, mData.sBlogKey);
            multipart.addFormField(DefineNetwork.POST_TEXT, mData.sText);

            multipart.addFormField(DefineNetwork.POST_CULTURE_TYPE, ((CultureItemData)mData).nArtType + "");
            multipart.addFormField(DefineNetwork.POST_CULTURE_TITLE, ((CultureItemData)mData).sTitle);
            //multipart.addFormField(DefineNetwork.POST_CULTURE_TAGS, ((CultureItemData)mData).sText);
            multipart.addFormField(DefineNetwork.POST_CULTURE_START_DATE, ((CultureItemData)mData).dStartDate);
            multipart.addFormField(DefineNetwork.POST_CULTURE_END_DATE, ((CultureItemData)mData).dEndDate);
            multipart.addFormField(DefineNetwork.POST_CULTURE_START_TIME, ((CultureItemData)mData).dStartTime);
            multipart.addFormField(DefineNetwork.POST_CULTURE_END_TIME, ((CultureItemData)mData).dEndTime);
            multipart.addFormField(DefineNetwork.POST_CULTURE_FEE, ((CultureItemData)mData).nFee + "");
            multipart.addFormField(DefineNetwork.POST_CULTURE_ADDRESS, ((CultureItemData)mData).sAddress);
           // multipart.addFormField(DefineNetwork.POST_CULTURE_LATITUDE, ((CultureItemData)mData).);
           // multipart.addFormField(DefineNetwork.POST_CULTURE_LONGITUDE, ((CultureItemData)mData).sText);

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
