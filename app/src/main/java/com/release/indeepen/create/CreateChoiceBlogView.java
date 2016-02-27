package com.release.indeepen.create;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.release.indeepen.R;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-08.
 */
public class CreateChoiceBlogView extends LinearLayout{
    TextView vTextBlog;
    ImageView vImgBlog;

    public CreateChoiceBlogView(Context context) {
        super(context);
        init();
    }

    public CreateChoiceBlogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_create_choice_blog, this);
        vTextBlog = (TextView) findViewById(R.id.text_create_choice_blog);
        vImgBlog =(ImageView) findViewById(R.id.img_create_choice_blog);
    }

    public void setData(BlogInfo data){
        vTextBlog.setText(data.sArtist);

        if(!TextUtils.isEmpty(data.thProfile)) {
            Picasso.with(getContext()).load(data.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
        }
    }
}