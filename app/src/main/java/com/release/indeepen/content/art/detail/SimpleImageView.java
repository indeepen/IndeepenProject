package com.release.indeepen.content.art.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.release.indeepen.R;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-01.
 */
public class SimpleImageView extends FrameLayout {

    ImageView vIMG;

    public SimpleImageView(Context context) {
        super(context);
        init();
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_simple_image, this);
        vIMG = (ImageView) findViewById(R.id.img_detail_img);
    }

    public void setData(String path) {
        //Uri uri = Uri.fromFile(new File(path));
        //ImageLoader.getInstance().displayImage(path, vIMG);
        Picasso.with(getContext()).load(path).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(getContext().getResources().getDisplayMetrics().widthPixels, 0).into(vIMG);

        //Picasso.with(getContext()).load(path).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(vIMG);
    }

    public ImageView getView(){
        return vIMG;
    }
}
