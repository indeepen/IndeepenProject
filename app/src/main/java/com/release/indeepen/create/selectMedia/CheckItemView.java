package com.release.indeepen.create.selectMedia;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.release.indeepen.R;

public class CheckItemView extends FrameLayout implements Checkable {
    ImageView checkView;
    boolean isChecked = false;

    public CheckItemView(Context context) {
        super(context);
        init();
    }

    public CheckItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.checked_item, this);
        checkView = (ImageView) findViewById(R.id.image_select_icon);
    }

    private void drawCheck() {
        if (isChecked) {
            checkView.setVisibility(VISIBLE);
            checkView.setAlpha(0.6f);

        } else {
            checkView.setVisibility(INVISIBLE);
        }

    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;
            drawCheck();
        }
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
