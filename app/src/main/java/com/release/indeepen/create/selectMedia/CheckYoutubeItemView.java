package com.release.indeepen.create.selectMedia;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.release.indeepen.R;

public class CheckYoutubeItemView extends FrameLayout implements Checkable {
    ImageView checkView, imageView;
    boolean isChecked = false;

    public CheckYoutubeItemView(Context context) {
        super(context);
        init();
    }

    public CheckYoutubeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.checked_youtube_item, this);
        checkView = (ImageView) findViewById(R.id.image_select_icon);
        imageView = (ImageView) findViewById(R.id.image_icon);
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(getContext().getResources().getDisplayMetrics().widthPixels/2)-10, (int)(getContext().getResources().getDisplayMetrics().widthPixels/2)-10);
        checkView.setLayoutParams(param);
        imageView.setLayoutParams(param);
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
