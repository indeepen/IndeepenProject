package com.release.indeepen.login;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.release.indeepen.SharedApplication;
import com.release.indeepen.user.UserData;

import java.util.Set;


public class PropertyManager {
    private static PropertyManager instance;
    public UserData mUser;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();

        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(SharedApplication.getContext());
        mEditor = mPrefs.edit();
        mUser = new UserData();
    }

    //페북

    private static final String FIELD_FACEBOOK_ID = "facebookId";

    public void setFacebookId(String id) {

        mEditor.putString(FIELD_FACEBOOK_ID, id);
        mEditor.commit();
    }

    public String getFaceBookId() {
        return mPrefs.getString(FIELD_FACEBOOK_ID, "");
    }

    //로컬

    public static final String KEY_ID = "id";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_NICKNAME = "name";

    public void setId(String id) {
        mEditor.putString(KEY_ID, id);
        mEditor.commit();
    }

    public String getId() {
        return mPrefs.getString(KEY_ID,"");
    }

    public void setPassword(String password) {
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(KEY_PASSWORD, "");
    }

    public void setName(String name){
        mEditor.putString(KEY_NAME, name);
        mEditor.commit();
    }
    public void setNickName(String nickname){
        mEditor.putString(KEY_NICKNAME, nickname);
        mEditor.commit();
    }




    public boolean isBackupSync() {
        return mPrefs.getBoolean("perf_sync", false);
    }


    private static final String REG_ID = "regToken";

    public void setRegistrationToken(String regId) {
        mEditor.putString(REG_ID, regId);
        mEditor.commit();
    }

    public String getRegistrationToken() {
        return mPrefs.getString(REG_ID, "");
    }

}
