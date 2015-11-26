package com.release.indeepen.content.art.singleList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-17.
 */
public class DoubleImageView extends LinearLayout {
    public DoubleImageView(Context context) {
        super(context);
        init();
    }

    public DoubleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_double_image, this);
    }
}
