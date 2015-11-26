package com.release.indeepen.blog;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.release.indeepen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogIntroView extends FrameLayout{


    public BlogIntroView(Context context) {
        super(context);
        init();
    }

    public BlogIntroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_blog_intro, this);

    }

}
