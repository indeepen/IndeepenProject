package com.release.indeepen.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.netSearch.data.HashTagResult;

/**
 * Created by lyo on 2015-11-24.
 */
public class SearchHashTagView extends LinearLayout{
    TextView vTextHash;
    HashTagResult mData;

    public SearchHashTagView(Context context) {
        super(context);
        init();
    }

    public SearchHashTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_search_hashtag, this);
        vTextHash = (TextView) findViewById(R.id.text_hashtag);
    }

    public void setData(HashTagResult data){
        mData = data;
        vTextHash.setText("#"+mData.sHashTag);
    }

}
