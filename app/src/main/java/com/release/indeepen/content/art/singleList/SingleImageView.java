package com.release.indeepen.content.art.singleList;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.comment.CommentActivity;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.detail.ContentDetailActivity;
import com.release.indeepen.culture.OptionPopupWindow;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.DELETEContentRequest;
import com.release.indeepen.management.networkManager.netComment.data.Comments;
import com.release.indeepen.management.networkManager.netCommon.CommonController;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-01.
 */
public class SingleImageView extends LinearLayout implements View.OnClickListener , OptionPopupWindow.OptionClickListener{

    LinearLayout like, comment;
    RelativeLayout option;
    ImageView vImg_like, vImg_comment, vImg_option;
    ImageView vThPro, vIMGContent, vIMGEmotion;
    DoubleImageView vIMGDoubleContent;
    TripleImageView vIMGTripleContent;
    TextView  vTextArtist, vTextDate, vText, vTextLike, vTextComm, vTextOption, vTextCommUser1, vTextCommUser2, vTextCommCon1, vTextCommCon2, vTextIMGCount;
    ContentImageData mData;
    SingleHeaderView vHeader;
    SingleFooterView vFooter;
    // Bundle mBundle;
    TextView[] comments;
    TextView[] commentUser;

    public SingleImageView(Context context) {
        super(context);
        init();
    }

    public SingleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_single_image, this);
        vHeader = (SingleHeaderView) findViewById(R.id.view_single_image_header);
        vFooter = (SingleFooterView) findViewById(R.id.view_single_image_footer);

        vThPro = (ImageView) vHeader.findViewById(R.id.img_image_single_thpro);
        vIMGEmotion = (ImageView) vHeader.findViewById(R.id.img_image_single_emotion);
        vTextArtist = (TextView) vHeader.findViewById(R.id.text_image_single_artist);
        vTextDate = (TextView) vHeader.findViewById(R.id.text_image_single_date);
        vText = (TextView) vHeader.findViewById(R.id.text_image_single_text);

        vThPro.setOnClickListener(this);
        vTextArtist.setOnClickListener(this);
        vTextDate.setOnClickListener(this);
        vText.setOnClickListener(this);

        //// content
        vIMGContent = (ImageView) findViewById(R.id.img_image_single_contents);
        vIMGContent.setOnClickListener(this);
        //LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getContext().getResources().getDisplayMetrics().widthPixels);
        vIMGDoubleContent = (DoubleImageView) findViewById(R.id.img_image_double_contents);
        vIMGTripleContent = (TripleImageView) findViewById(R.id.img_image_triple_contents);

        vIMGDoubleContent.setOnClickListener(this);
        vIMGTripleContent.setOnClickListener(this);
        vTextIMGCount = (TextView) vIMGTripleContent.findViewById(R.id.text_triple3);
        //vIMGContent.setLayoutParams(imgParam);

        //// foot
        vTextLike = (TextView) vFooter.findViewById(R.id.text_image_single_like);
        vTextComm = (TextView) vFooter.findViewById(R.id.text_image_single_comm);
        vTextCommUser1 = (TextView) vFooter.findViewById(R.id.text_image_single_nick1);
        vTextCommUser2 = (TextView) vFooter.findViewById(R.id.text_image_single_nick2);
        vTextCommCon1 = (TextView) vFooter.findViewById(R.id.text_image_single_comm1);
        vTextCommCon2 = (TextView) vFooter.findViewById(R.id.text_image_single_comm2);

        comments = new TextView[]{vTextCommCon1, vTextCommCon2};
        commentUser = new TextView[]{vTextCommUser1, vTextCommUser2};

        vTextCommUser1.setOnClickListener(this);
        vTextCommUser2.setOnClickListener(this);
        vTextCommCon1.setOnClickListener(this);
        vTextCommCon2.setOnClickListener(this);
        vTextLike.setOnClickListener(this);
        vTextComm.setOnClickListener(this);


        vImg_like = (ImageView) vFooter.findViewById(R.id.image_single_like);
        vImg_comment = (ImageView) vFooter.findViewById(R.id.image_single_comm);
        vImg_option = (ImageView) vFooter.findViewById(R.id.image_single_option);
        vImg_like.setOnClickListener(this);
        vImg_comment.setOnClickListener(this);
        vImg_option.setOnClickListener(this);

        like = (LinearLayout) findViewById(R.id.like);
        comment = (LinearLayout) findViewById(R.id.comment);
        option = (RelativeLayout) findViewById(R.id.option);

        like.setOnClickListener(this);
        comment.setOnClickListener(this);
        option.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_image_single_contents:
            case R.id.text_image_single_text:
            case R.id.img_image_single_emotion :
            case R.id.text_image_single_nick1:
            case R.id.text_image_single_nick2:
            case R.id.text_image_single_comm1:
            case R.id.text_image_single_comm2:
            case R.id.img_image_double_contents:
            case R.id.img_image_triple_contents:{
                Intent mIntent = new Intent(getContext(), ContentDetailActivity.class);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData.sContentKey);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, mData.nArtType);
                getContext().startActivity(mIntent);
                break;
            }
            case R.id.text_image_single_artist:
            case R.id.img_image_single_thpro: {
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                if(DefineContentType.BLOG_TYPE_MYBLOG == mData.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                }else{
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                //mIntent.putExtra(DefineContentType.KEY_ON_NEW_GET_DATA_URL, ); // 이동시 다시 받아올 Data URL
                getContext().startActivity(mIntent);
                break;
            }
            case R.id.like:
            case R.id.image_single_like:
            case R.id.text_image_single_like: {
                Toast.makeText(getContext(), "좋다", Toast.LENGTH_SHORT).show();
                vImg_like.setSelected(!vImg_like.isSelected());
                break;
            }
            case R.id.comment:
            case R.id.image_single_comm:
            case R.id.text_image_single_comm: {
                /*Intent mIntent = new Intent(getContext(), CommentListActivity.class);
                getContext().startActivity(mIntent);*/
                Intent mIntent = new Intent(getContext(), CommentActivity.class);
                mIntent.putExtra(DefineNetwork.CONTENT_KEY, mData.sContentKey);
                getContext().startActivity(mIntent);
                break;
            }
            case R.id.option:
            case R.id.image_single_option: {
                onOptionPopupWindow(v);
                break;
            }
        }
    }

    @Override
    public void onClickEvent(int mode) {
        switch (mode){
            case OptionPopupWindow.UNLIKE:{
                break;
            }
            case OptionPopupWindow.SHARE:{
                break;
            }
            case OptionPopupWindow.EDIT:{
                break;
            }
            case OptionPopupWindow.DELETE:{
                DELETEContentRequest request = new DELETEContentRequest();
                request.setURL(String.format(DefineNetwork.DELETE_CONTENT, mData.sContentKey));
                CommonController.getInstance().deleteContent(request, new NetworkProcess.OnResultListener<String>() {
                    @Override
                    public void onSuccess(NetworkRequest<String> request, String result) {
                        Toast.makeText(getContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<String> request, int code) {
                        Toast.makeText(getContext(), code + "- error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }

    private void onOptionPopupWindow(View view) {
        OptionPopupWindow popup = new OptionPopupWindow(getContext());
        popup.setOnOptionClick(this);
        popup.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popup.showAsDropDown(comment, Gravity.RELATIVE_LAYOUT_DIRECTION, 16, 0);
        }
    }



    public void setData(ContentImageData data) {
        if (null == data) return;
        mData = data;
        //ImageLoader.getInstance().displayImage(mData.mUserData.thProfile, vThPro);
        Picasso.with(getContext()).load(mData.mUserData.thProfile).fit().into(vThPro);
        vTextIMGCount.setVisibility(GONE);

        if(0 == data.arrIMGs.size()){
            vIMGContent.setVisibility(GONE);
            vIMGDoubleContent.setVisibility(GONE);
            vIMGTripleContent.setVisibility(GONE);
        }

        if(1 == data.arrIMGs.size()) {
            vIMGContent.setVisibility(VISIBLE);
            vIMGDoubleContent.setVisibility(GONE);
            vIMGTripleContent.setVisibility(GONE);

            Picasso.with(getContext()).load(data.arrIMGs.get(0)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(getContext().getResources().getDisplayMetrics().widthPixels, 0).into(vIMGContent);
            //ImageLoader.getInstance().displayImage(data.arrIMGs.get(0), vIMGContent);
        }else if(2 == data.arrIMGs.size()) {
            vIMGDoubleContent.setVisibility(VISIBLE);
            vIMGContent.setVisibility(GONE);
            vIMGTripleContent.setVisibility(GONE);

            ImageView img1 = (ImageView) vIMGDoubleContent.findViewById(R.id.img_double1);
            ImageView img2 = (ImageView) vIMGDoubleContent.findViewById(R.id.img_double2);
            Picasso.with(getContext()).load(data.arrIMGs.get(0)).placeholder(R.drawable.ic_empty)
                    .error(R.drawable.ic_error).into(img1);
            Picasso.with(getContext()).load(data.arrIMGs.get(1)).placeholder(R.drawable.ic_empty)
                    .error(R.drawable.ic_error).into(img2);
//            ImageLoader.getInstance().displayImage(data.arrIMGs.get(0), img1);
//            ImageLoader.getInstance().displayImage(data.arrIMGs.get(1), img2);
        }else if(3 <= data.arrIMGs.size()) {
            vIMGTripleContent.setVisibility(VISIBLE);
            vIMGContent.setVisibility(GONE);
            vIMGDoubleContent.setVisibility(GONE);

            LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, getContext().getResources().getDisplayMetrics().widthPixels);
            vIMGTripleContent.setLayoutParams(param);
            ImageView img1 = (ImageView) vIMGTripleContent.findViewById(R.id.img_triple1);
            ImageView img2 = (ImageView) vIMGTripleContent.findViewById(R.id.img_triple2);
            ImageView img3 = (ImageView) vIMGTripleContent.findViewById(R.id.img_triple3);

//            ImageLoader.getInstance().displayImage(data.arrIMGs.get(0), img1);
//            ImageLoader.getInstance().displayImage(data.arrIMGs.get(1), img2);
//            ImageLoader.getInstance().displayImage(data.arrIMGs.get(2), img3);
            Picasso.with(getContext()).load(data.arrIMGs.get(0)).placeholder(R.drawable.ic_empty)
                    .error(R.drawable.ic_error).into(img1);
            Picasso.with(getContext()).load(data.arrIMGs.get(1)).placeholder(R.drawable.ic_empty)
                    .error(R.drawable.ic_error).into(img2);
            Picasso.with(getContext()).load(data.arrIMGs.get(2)).placeholder(R.drawable.ic_empty)
                    .error(R.drawable.ic_error).into(img3);

            if(0 < data.arrIMGs.size() - 3 ) {
                vTextIMGCount.setVisibility(VISIBLE);
                vTextIMGCount.setText("+"+ (data.arrIMGs.size() - 3));
            }
        }

        vTextArtist.setText(mData.mUserData.sArtist);
        vTextDate.setText(DateManager.getInstance().getPastTime(mData.sWriteDate));
        vTextLike.setText(mData.nLikeCount+"");
        vTextComm.setText(mData.nCommentCount+"");

        switch (data.nEmotion){
            case DefineContentType.EMO_NONE: {
                vIMGEmotion.setImageResource(R.drawable.icon_nofeeling);
               // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_nofeeling, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_HAPPY:{
                vIMGEmotion.setImageResource(R.drawable.icon_happy);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_happy, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_LOVE:{
                vIMGEmotion.setImageResource(R.drawable.icon_love);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_love, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_SAD:{
                vIMGEmotion.setImageResource(R.drawable.icon_sad);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_sad, vIMGEmotion);
                break;
            }
            case DefineContentType.EMO_ANGRY:{
                vIMGEmotion.setImageResource(R.drawable.icon_angry);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.icon_angry, vIMGEmotion);
                break;
            }
        }

        if(TextUtils.isEmpty(mData.sText) || mData.sText.equalsIgnoreCase("null")){
           // vText.setVisibility(GONE);
            vText.setText("");
        }else{

            vText.setText(mData.sText);
        }

        if(0 < mData.arrComment.size()) {
            int idx=0;
            for(Comments comm : mData.arrComment) {
                if(null != comm.mWriter) {
                    commentUser[idx].setVisibility(VISIBLE);
                    comments[idx].setVisibility(VISIBLE);
                    commentUser[idx].setText(comm.mWriter.sArtist);
                    comments[idx].setText(comm.sComm);
                }
                idx++;
            }
        }else{
            commentUser [0].setVisibility(INVISIBLE);
            comments[0].setVisibility(INVISIBLE);
            commentUser [1].setVisibility(INVISIBLE);
            comments[1].setVisibility(INVISIBLE);
        }


    }




}
