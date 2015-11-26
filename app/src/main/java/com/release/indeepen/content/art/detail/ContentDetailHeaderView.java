package com.release.indeepen.content.art.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-05.
 */
public class ContentDetailHeaderView extends LinearLayout{

    public ContentDetailHeaderView(Context context) {
        super(context);
        init();
    }

    public ContentDetailHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_content_detail_header, this);

    }
}
