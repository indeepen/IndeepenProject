package com.release.indeepen.management.networkManager.netCulture.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netArt.data.Writer;

import java.io.Serializable;

/**
 * Created by lyo on 2015-11-17.
 */
public class Tags implements Serializable{
    @SerializedName("_user")
    public Writer sWriter;

    @SerializedName("point")
    public Point mPoint;
}
