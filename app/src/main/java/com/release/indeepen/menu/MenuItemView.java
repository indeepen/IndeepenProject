package com.release.indeepen.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.R;

/**
 * Created by Lady on 2015. 11. 16..
 */
public class MenuItemView extends LinearLayout implements View.OnClickListener {

    public MenuItemView(Context context) {
        super(context);
        init();
    }

    public MenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    RelativeLayout user, space, add_art_space, artisit, setting, logout;
    ImageView img_art_space;
    TextView text_art_space;


    private void init() {
        inflate(getContext(), R.layout.view_add_space, this);

        //   user = (RelativeLayout)findViewById(R.id.user);
        //   space = (RelativeLayout)findViewById(R.id.space);
        add_art_space = (RelativeLayout) findViewById(R.id.art_space);
        //   artisit = (RelativeLayout)findViewById(R.id.artisit);
        //   setting = (RelativeLayout)findViewById(R.id.setting);
        //   logout = (RelativeLayout)findViewById(R.id.logout);
        img_art_space = (ImageView) findViewById(R.id.img_art_space);
        text_art_space = (TextView) findViewById(R.id.text_art_space);


        add_art_space.setOnClickListener(this);
        img_art_space.setOnClickListener(this);
        text_art_space.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_art_space: {
                Toast.makeText(getContext(), "선택부분 click", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    public void setItemData(MenuItemData data) {
        //ImageLoader.getInstance().displayImage(file Path, view 객체)
        // ImageLoader.getInstance().displayImage(data.mUserData.thProfile, image_user);
        text_art_space.setText(data.sArtSpaceName);
        //text_comment.setText(data.nCommentCount + "");
        //   text_uploadtime.setText(data.dWriteDate);
    }
}