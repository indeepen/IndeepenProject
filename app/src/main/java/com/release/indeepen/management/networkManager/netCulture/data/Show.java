package com.release.indeepen.management.networkManager.netCulture.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lyo on 2015-11-17.
 */
public class Show {
    @SerializedName("title")
    public String sTitle;

    @SerializedName("type")
    public int nType;

    @SerializedName("startDate")
    public String dStartDate;

    @SerializedName("endDate")
    public String dEndDate;

    @SerializedName("startTime")
    public String dStartTime;

    @SerializedName("endTime")
    public String dEndTime;

    @SerializedName("fee")
    public int nFee;

    @SerializedName("location")
    public Location mLocation;

    @SerializedName("tags")
    public List<Tags> arrTags;

}
