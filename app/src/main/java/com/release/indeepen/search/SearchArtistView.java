package com.release.indeepen.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netSearch.data.HashTagResult;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-24.
 */
public class SearchArtistView extends LinearLayout{
    TextView vTextArtist;
    ImageView vIMGPro;
    BlogInfo mData;

    public SearchArtistView(Context context) {
        super(context);
        init();
    }

    public SearchArtistView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_search_artist, this);
        vTextArtist = (TextView) findViewById(R.id.text_search_name);
        vIMGPro = (ImageView) findViewById(R.id.img_search_profile);
    }

    public void setData(BlogInfo data){
        mData = data;
        vTextArtist.setText(mData.sArtist);
        //ImageLoader.getInstance().displayImage(mData.thProfile, vIMGPro);

        Picasso.with(getContext()).load(mData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vIMGPro);
    }

}
