package com.release.indeepen.content.art.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ExpandImageActivity extends AppCompatActivity {

    ImageView vImage, vFake;

    byte[] byteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_image);
        vImage = (ScaleImageView) findViewById(R.id.img_expand);

        init();

    }

    private void init() {

        String path = getIntent().getStringExtra(DefineContentType.EXPAND_IMG);
        //ImageLoader.getInstance().displayImage(path, vImage);
        Picasso.with(this).load(path).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(vImage);

    }



}