package com.release.indeepen.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-08.
 */
public class SearchView extends RelativeLayout {
    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_search, this);
    }
}
