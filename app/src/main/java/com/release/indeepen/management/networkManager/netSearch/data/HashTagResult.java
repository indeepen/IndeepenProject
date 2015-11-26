package com.release.indeepen.management.networkManager.netSearch.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lyo on 2015-11-24.
 */
public class HashTagResult implements Search {
    @SerializedName("_id")
    public String sHashTag;

    @SerializedName("cnt")
    public int nCount;
}
