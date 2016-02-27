package com.release.indeepen.management.networkManager;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by lyo on 2015-11-10.
 */
public class NetworkProcess<T> implements Runnable {


    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    Handler mHandler = new Handler(Looper.getMainLooper());
    NetworkRequest<T> request;
    OnResultListener<T> listener;
    boolean isSession;
    String sCookies;
    int code;


    public NetworkProcess(NetworkRequest<T> request, OnResultListener<T> listener) {
        this.request = request;
        this.listener = listener;
    }

    public void saveCookie(HttpURLConnection conn) {
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                Log.e("save-cookie", cookie);
            }
        }

    }

    @Override
    public void run() {
        code = -1;
        try {
            URL url = request.getURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
            String cookie = TextUtils.join(";", msCookieManager.getCookieStore().getCookies());
            if(!TextUtils.isEmpty(cookie)) {
                conn.setRequestProperty("Cookie", cookie);
                Log.e("send-cookie", cookie);
            }

            request.setRequstMethod(conn);
            //request.setOutput(conn);
            Log.w("URL: ", request.getURL().toString());
            code = conn.getResponseCode();

            if (code == HttpURLConnection.HTTP_OK) {

                saveCookie(conn);


                InputStream is = conn.getInputStream();
                final T object = request.parsing(is);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!request.isCancel()) {
                            listener.onSuccess(request, object);
                        }
                    }
                });
                return;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!request.isCancel()) {
                    listener.onFail(request, code);
                }
            }
        });
    }

    public interface OnResultListener<T> {
        void onSuccess(NetworkRequest<T> request, T result);

        void onFail(NetworkRequest<T> request, int code);
    }

}
