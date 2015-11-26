package com.release.indeepen.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.release.indeepen.R;

/**
 * Created by lyo on 2015-10-31.
 */
public class NotiTextView extends RelativeLayout {

    ImageView vIMGPro, vIMGCon;
    TextView vTextMSG;

    public NotiTextView(Context context) {
        super(context);

        init();
    }

    public NotiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_notify_text, this);
    }

    public void setData(PushData data) {
        vIMGPro = (ImageView) findViewById(R.id.img_push_text_thpro);
        vTextMSG = (TextView) findViewById(R.id.text_push_text_msg);

        if (null != data) {
            //vIMGPro.setImageResource(data.thProfile);
            vTextMSG.setText(data.sMSG);
        }
    }
}