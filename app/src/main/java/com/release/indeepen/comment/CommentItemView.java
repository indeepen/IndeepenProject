package com.release.indeepen.comment;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.netComment.data.Comments;
import com.squareup.picasso.Picasso;

/**
 * Created by Lady on 2015. 11. 2..
 */
public class CommentItemView extends LinearLayout {

    ImageView image_user;
    TextView text_username, text_comment, text_uploadtime;
    Comments mData;
    EditText text_input;
    OnImageClickListener mListener;
    public CommentItemView(Context context) {
        super(context);
        init();
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener;
    }

    private void init() {
        inflate(getContext(), R.layout.view_list_comment, this);
        image_user = (ImageView) findViewById(R.id.image_user);
        image_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.mWriter.sBlogKey);
                if (DefineContentType.BLOG_TYPE_MYBLOG == mData.mWriter.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                } else {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                getContext().startActivity(mIntent);
                ((CommentActivity) getContext()).finish();
                /*if (mListener != null) {
                    mListener.onImageClick(CommentItemView.this, mData);
                }*/
            }
        });

        text_username = (TextView) findViewById(R.id.text_username);
        text_comment = (TextView) findViewById(R.id.text_comment);
        text_uploadtime = (TextView) findViewById(R.id.text_uploadtime);

    }

    public void setItemData(Comments data) {
        mData = data;
        if (data.mWriter != null) {
            //ImageLoader.getInstance().displayImage(data.mWriter.thProfile, image_user);
            Picasso.with(getContext()).load(data.mWriter.thProfile).fit().placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(image_user);
            text_username.setText(data.mWriter.sArtist);
        }
        text_comment.setText(data.sComm);
        text_uploadtime.setText(DateManager.getInstance().getPastTime(data.dWriteDate));
    }

    public void changeData() {
       /* if (mData == null) return;
        image_user.setImageDrawable(mData.userimage);
        text_username.setText(mData.username);
        text_comment.setText(mData.comment);
        text_uploadtime.setText(mData.uploadtime);*/
    }

    public interface OnImageClickListener {
        void onImageClick(CommentItemView view, Comments date);
    }

    /*private void initData() {
        String[] items = getResources().getStringArray(R.array.list_item);
        for (String s : items) {
            mAdapter.add(s);
        }
    }*/
}
