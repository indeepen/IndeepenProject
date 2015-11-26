package com.release.indeepen.blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-11-03.
 */
public class PopupIMissU extends PopupWindow {
    Context mContext;

    public PopupIMissU(Context context) {
        super(context);
        // super(LayoutInflater.from(context).inflate(R.layout.popup_imissu, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_imissu, null);
        Button btn = (Button) view.findViewById(R.id.btn_pop_imissu_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "+1", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btn = (Button) view.findViewById(R.id.btn_pop_imissu_no);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "취소", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        setContentView(view);

        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
