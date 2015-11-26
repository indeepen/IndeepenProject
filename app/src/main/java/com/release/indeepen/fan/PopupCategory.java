package com.release.indeepen.fan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-05.
 */
public class PopupCategory extends PopupWindow {
    Context mContext;
    public Button btnAll, btnPaint, btnPicture, btnMusic, btnVideo, btnCulture;
    public PopupCategory(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_category, null);

        btnAll = (Button) view.findViewById(R.id.pop_cate_all);

        btnPaint = (Button) view.findViewById(R.id.pop_cate_paint);

        btnPicture = (Button) view.findViewById(R.id.pop_cate_picture);

        btnMusic = (Button) view.findViewById(R.id.pop_cate_music);

        btnVideo = (Button) view.findViewById(R.id.pop_cate_video);

        btnCulture = (Button) view.findViewById(R.id.pop_cate_culture);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }
}

