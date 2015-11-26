package com.release.indeepen.management.networkManager.netLogin.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lyo on 2015-11-25.
 */
public class LoginData {
    @SerializedName("userKey")
    public String userKey;
    @SerializedName("activityBlogKey")
    public String activityBlogKey;
    @SerializedName("artistBlogKey")
    public String artistBlogKey;
    @SerializedName("spaceBlogKeys")
    public List<String> arrSpaceBlogKeys;
}
