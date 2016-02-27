package com.release.indeepen.blog.tripleGrid;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-10-31.
 */
public class ThumbImageView extends FrameLayout {

    ImageView thIMG;

    public ThumbImageView(Context context) {
        super(context);
        init();
    }

    public ThumbImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_thumb_image, this);
        thIMG = (ImageView) findViewById(R.id.img_item);
    }

    void setData(ContentData data) {
        if(!TextUtils.isEmpty(data.sThumb)) {
            //ImageLoader.getInstance().displayImage(data.thIMG, thIMG);
            Picasso.with(getContext()).load(data.sThumb).fit().centerCrop().placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(thIMG);
        }else if(!TextUtils.isEmpty(((ContentYoutubeData)data).sYouTubePath)) {
            Picasso.with(getContext()).load("https://i1.ytimg.com/vi/" + ((ContentYoutubeData)data).sYouTubePath + "/mqdefault.jpg").fit().centerCrop().placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(thIMG);
        }
        //thIMG.setImageResource(path);
    }

}
