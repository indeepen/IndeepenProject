package com.release.indeepen.create;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-08.
 */
public class CreateHeader extends LinearLayout {
   /* ImageView vImgBlog, vImgEmoNone, vImgEmoHappy, vImgEmoLove, vImgEmoSad, vImgEmoAngry;*/

    public CreateHeader(Context context) {
        super(context);
        init();
    }

    public CreateHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_create_header, this);
    }
}
