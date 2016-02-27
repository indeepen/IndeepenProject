package com.release.indeepen.management.networkManager.netMyBlog.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyo on 2015-11-29.
 */
public class BlogInfoListResult implements Serializable{
    @SerializedName("result")
    public List<BlogInfo> arrBlogs;
}
