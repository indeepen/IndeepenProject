package com.release.indeepen.blog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 9..
 */
public class ProfileDialog extends PopupWindow {
    Context mContext;
    ImageButton btnModify, btnShow, btnUpload, btnOther;


   /* public final static int UNLIKE = 0;
    public final static int SHARE = 1;
    public final static int EDIT = 2;
    public final static int DELETE = 3;*/


    public interface OptionClickListener {
        void onClickEvent(int mode);
    }

    OptionClickListener mListener;

    public void setOnOptionClick(OptionClickListener listener) {
        mListener = listener;
    }

    public ProfileDialog(Context context) {
        super(context);
        mContext = context;
        final View view = LayoutInflater.from(context).inflate(R.layout.view_dialog, null);

        btnModify = (ImageButton) view.findViewById(R.id.blog_modify);

        btnShow = (ImageButton) view.findViewById(R.id.blog_show);

        btnUpload = (ImageButton) view.findViewById(R.id.blog_up);

        btnOther = (ImageButton) view.findViewById(R.id.blog_other);

        setContentView(view);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

}


