package com.release.indeepen.culture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 9..
 */
public class TypePopupWindow extends PopupWindow {
    Context mContext;
    Button btnAll, btnDisplay, btnPerformance, btnShow, btnArt, btnFestival;


    public TypePopupWindow(Context context) {
        super(context);
        mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.popup_culture_type, null);


        btnAll = (Button)view.findViewById(R.id.btn_all);

        btnDisplay = (Button)view.findViewById(R.id.btn_display);

        btnPerformance = (Button)view.findViewById(R.id.btn_performance);

        btnShow = (Button)view.findViewById(R.id.btn_show);

        btnArt = (Button)view.findViewById(R.id.btn_art);

        btnFestival = (Button)view.findViewById(R.id.btn_festival);


        setContentView(view);

       /* setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*/
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }


}
