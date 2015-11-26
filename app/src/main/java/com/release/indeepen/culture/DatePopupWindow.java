package com.release.indeepen.culture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 21..
 */
public class DatePopupWindow extends PopupWindow {
    Context mContext;
    Button btn;

    public DatePopupWindow(Context context) {
        super(context);
        mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.view_date, null);


        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

    }


}
