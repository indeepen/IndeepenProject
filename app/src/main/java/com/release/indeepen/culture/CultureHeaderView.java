package com.release.indeepen.culture;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 19..
 */
public class CultureHeaderView  extends LinearLayout {
    public CultureHeaderView(Context context) {
        super(context);
        init();
    }

    public CultureHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_culture_header, this);
    }
}
