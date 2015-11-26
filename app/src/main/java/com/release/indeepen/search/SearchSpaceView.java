package com.release.indeepen.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.netSearch.data.HashTagResult;
import com.release.indeepen.space.SpaceInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-24.
 */
public class SearchSpaceView extends LinearLayout{
    TextView vTextSpace;
    ImageView vIMGPro;
    SpaceInfo mData;

    public SearchSpaceView(Context context) {
        super(context);
        init();
    }

    public SearchSpaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_search_space, this);
        vTextSpace = (TextView) findViewById(R.id.text_search_name);
        vIMGPro = (ImageView) findViewById(R.id.img_search_profile);
    }

    public void setData(SpaceInfo data){
        mData = data;
        vTextSpace.setText(mData.sSpaceName);
       // ImageLoader.getInstance().displayImage(mData.thProfile, vIMGPro);

        Picasso.with(getContext()).load(mData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vIMGPro);
    }

}
