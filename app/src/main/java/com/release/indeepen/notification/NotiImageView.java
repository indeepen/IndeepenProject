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
public class NotiImageView extends RelativeLayout {

    ImageView vIMGPro, vIMGCon;
    TextView vTextMSG;

    public NotiImageView(Context context) {
        super(context);
        init();
    }

    public NotiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        vIMGPro = (ImageView) findViewById(R.id.img_push_img_thpro);
        vTextMSG = (TextView) findViewById(R.id.text_push_img_msg);

        inflate(getContext(), R.layout.view_notify_image, this);
    }

    public void setData(PushData data) {
       // vIMGPro.setImageResource(data.thProfile);
        vTextMSG.setText(data.sMSG);
    }

}
