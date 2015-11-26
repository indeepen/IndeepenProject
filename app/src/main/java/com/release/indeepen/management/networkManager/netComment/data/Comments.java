package com.release.indeepen.management.networkManager.netComment.data;

import com.google.gson.annotations.SerializedName;
import com.release.indeepen.management.networkManager.netArt.data.Writer;

import java.io.Serializable;

/**
 * Created by lyo on 2015-11-11.
 */
public class Comments implements Serializable{
    @SerializedName("_id")
    public String sCommID;
    @SerializedName("_writer")
    public Writer mWriter;
    @SerializedName("content")
    public String sComm;
    @SerializedName("createAt")
    public String dWriteDate;
}
