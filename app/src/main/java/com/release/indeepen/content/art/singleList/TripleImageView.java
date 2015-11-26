package com.release.indeepen.content.art.singleList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-17.
 */
public class TripleImageView extends GridLayout {

    public TripleImageView(Context context) {
        super(context);
        init();
    }

    public TripleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_triple_image, this);
    }
}
