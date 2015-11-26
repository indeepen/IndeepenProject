package com.release.indeepen.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-10-31.
 */
public class UserData implements Serializable {

    @SerializedName("_id")
    public String sBlogKey;

    @SerializedName("nick")
    public String sArtist;

    @SerializedName("email")
    public String sEmail;

    @SerializedName("name")
    public String sName;

    @SerializedName("_user")
    public String sUserkey;

    public boolean isPublic;

    @SerializedName("phone")
    public String sPhoneNum;

    @SerializedName("intro")
    public String sIntrodution;

    @SerializedName("type")
    public int nPostType;

    public String thProfile;
    public List<String> arrSpaceKeys;
    public String sActiveBlogKey;


}
