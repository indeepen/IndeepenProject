package com.release.indeepen.management.jsonManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lyo on 2015-11-26.
 */
public class IndeepenJsonParser {

    private static IndeepenJsonParser instance;

    public static IndeepenJsonParser getInstance(){
        if(null == instance){
            instance = new IndeepenJsonParser();
        }

        return instance;
    }

    private IndeepenJsonParser(){}

    public JSONObject getJsonObject(InputStream is) {

        //Create input stream
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            //returns the json object
            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if something went wrong, return null
        return null;
    }
}
