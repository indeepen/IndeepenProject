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
public class PopupEmotion extends PopupWindow {
    Context mContext;
    public Button btnAll, btnNone, btnHappy, btnLove, btnSad, btnAngry;

    public PopupEmotion(Context context) {
        super(context);
        // super(LayoutInflater.from(context).inflate(R.layout.popup_imissu, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_emotion, null);

        btnAll = (Button) view.findViewById(R.id.pop_emo_all);

        btnNone = (Button) view.findViewById(R.id.pop_emo_empty);

        btnHappy = (Button) view.findViewById(R.id.pop_emo_happy);

        btnLove = (Button) view.findViewById(R.id.pop_emo_love);

        btnSad = (Button) view.findViewById(R.id.pop_emo_sad);

        btnAngry = (Button) view.findViewById(R.id.pop_emo_angry);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }




}

