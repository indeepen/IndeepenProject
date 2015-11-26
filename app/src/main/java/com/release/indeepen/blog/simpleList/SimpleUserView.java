package com.release.indeepen.blog.simpleList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.R;
import com.release.indeepen.user.UserData;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-02.
 */
public class SimpleUserView extends RelativeLayout {

    ImageView vThPro;
    TextView vTextArtist;

    public SimpleUserView(Context context) {
        super(context);
        init();
    }

    public SimpleUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_simple_user, this);
        vThPro = (ImageView) findViewById(R.id.img_simple_thPro);
        vTextArtist = (TextView) findViewById(R.id.text_simple_artist);

    }

    public void setData(UserData data) {
        //vThPro.setImageResource(data.thProfile);
        //ImageLoader.getInstance().displayImage(data.thProfile, vThPro);

        Picasso.with(getContext()).load(data.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vThPro);
        vTextArtist.setText(data.sArtist);

    }
}
