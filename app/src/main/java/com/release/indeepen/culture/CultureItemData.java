package com.release.indeepen.culture;

import com.release.indeepen.content.ContentData;
import com.release.indeepen.management.networkManager.netCulture.data.Tags;
import com.release.indeepen.user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lady on 2015. 11. 2..
 */
public class CultureItemData extends ContentData {
    //public String username;
//    public String uploadtime;

    public String sTitle;
    public List<String> arrIMGs;
    public String dStartDate;
    public String dEndDate;
    public String dStartTime;
    public String dEndTime;
    public int nFee;
    public String sAddress;
    public List<Tags> arrTags;
    public String option;

    public CultureItemData(){
        mUserData = new UserData();
        arrIMGs = new ArrayList<String>();
    }
    //public String like;
    //public String comment;



}
