package com.release.indeepen.management.networkManager.netArt;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResultList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lyo on 2015-11-10.
 */
public class ArtListRequest extends NetworkRequest<ContentResultList> {
    String sURL;
    @Override
    public URL getURL() throws MalformedURLException {
        return new URL(sURL);
    }

    public void setURL(String url) {
        sURL = url;
    }

    public void setRequstMethod(HttpURLConnection conn){
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
    public ContentResultList parsing(InputStream is) {
        ContentResultList arrResult = null;

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

            arrResult = new Gson().fromJson(reader, ContentResultList.class);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrResult;
    }
}
   /*
   String keyword;
    int start;
    int display;
    private static final String MOVIE_URL = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&query=%s&display=%s&start=%s&target=movie";
    public NaverMovieRequest(String keyword, int start, int display) {
        this.keyword = keyword;
        this.start = start;
        this.display = display;
    }

    @Override
    public URL getURL() throws MalformedURLException {
        try {
            String urlText = String.format(MOVIE_URL, URLEncoder.encode(keyword, "utf-8"), display, start);
            return new URL(urlText);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public NaverMovies parsing(InputStream is) {
        XMLParser parser = new XMLParser();
        NaverMovies movies = parser.fromXml(is, "channel", NaverMovies.class);
        return movies;
    }
    */

