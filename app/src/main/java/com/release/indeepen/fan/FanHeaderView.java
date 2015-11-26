package com.release.indeepen.fan;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-06.
 */
public class FanHeaderView extends LinearLayout {

    public FanHeaderView(Context context) {
        super(context);
        init();
    }

    public FanHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_fan_header, this);
    }
}
