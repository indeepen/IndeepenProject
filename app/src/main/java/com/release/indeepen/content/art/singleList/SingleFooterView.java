package com.release.indeepen.content.art.singleList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-05.
 */
public class SingleFooterView extends LinearLayout{



    public SingleFooterView(Context context) {
        super(context);
        init();
    }

    public SingleFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_single_footer, this);
    }

}
