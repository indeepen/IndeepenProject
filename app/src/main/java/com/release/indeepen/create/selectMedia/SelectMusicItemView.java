package com.release.indeepen.create.selectMedia;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-13.
 */
public class SelectMusicItemView extends LinearLayout implements Checkable {

    boolean isChecked = false;
    public SelectMusicItemView(Context context) {
        super(context);
        init();
    }

    public SelectMusicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_select_music_item, this);
    }

    private void drawCheck() {
        if (isChecked) {
            setBackgroundColor(getResources().getColor(R.color.colorCheck));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.white));
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
