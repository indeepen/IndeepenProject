package com.release.indeepen.content.art;

import com.release.indeepen.content.ContentData;
import com.release.indeepen.user.UserData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyo on 2015-10-31.
 */
public class ContentImageData extends ContentData {

    public List<String> arrIMGs;
    public List<String> arrThumbs;
    public List<String> arrIMGName;

    public ContentImageData(){
        mUserData = new UserData();
        arrIMGs = new ArrayList<String>();
        arrIMGName = new ArrayList<String>();
        arrThumbs = new ArrayList<String>();
    }
}
