package com.release.indeepen.blog;

import com.release.indeepen.user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-10-31.
 */
public class BlogIntroData{
    UserData mUserData;
    List<String> arrFans;
    List<String> arrMissYous;
    int nBlogType;

    BlogIntroData(){
        mUserData = new UserData();
        arrFans = new ArrayList<String>();
        arrMissYous = new ArrayList<String>();
    }
}
