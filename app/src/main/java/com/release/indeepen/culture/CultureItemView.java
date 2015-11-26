package com.release.indeepen.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.comment.CommentActivity;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.DELETEContentRequest;
import com.release.indeepen.management.networkManager.netCommon.CommonController;
import com.release.indeepen.management.networkManager.netCommon.PUTLikeRequest;
import com.squareup.picasso.Picasso;

/**
 * Created by Lady on 2015. 11. 2..
 */
public class CultureItemView extends LinearLayout implements View.OnClickListener , OptionPopupWindow.OptionClickListener{

    public CultureItemView(Context context) {
        super(context);
        init();
    }

    public CultureItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    LinearLayout like, comment;
    RelativeLayout btn_user, btn_culture_detail, option;
    ImageView image_user, image_fee, image_ing, image_main, img_tag, img_like, img_option, divider;
    TextView text_username, text_uploadtime, text_title, text_date, text_time, text_address, text_comment, text_like;
    CultureItemData mData;


    private void init() {
        inflate(getContext(), R.layout.view_list_culture, this);

        btn_user = (RelativeLayout) findViewById(R.id.btn_user);
        btn_culture_detail = (RelativeLayout) findViewById(R.id.btn_culture_detail);
        image_user = (ImageView) findViewById(R.id.image_user);
        text_username = (TextView) findViewById(R.id.text_username);
        text_uploadtime = (TextView) findViewById(R.id.text_uploadtime);
        image_fee = (ImageView) findViewById(R.id.image_fee);
        image_ing = (ImageView) findViewById(R.id.image_ing);
        text_title = (TextView) findViewById(R.id.text_title);
        image_main = (ImageView) findViewById(R.id.image_main);
        text_date = (TextView) findViewById(R.id.text_comm_date);
        text_time = (TextView) findViewById(R.id.text_time);
        like = (LinearLayout) findViewById(R.id.like);
        comment = (LinearLayout) findViewById(R.id.comment);
        img_tag = (ImageView) findViewById(R.id.img_tag);
        img_option = (ImageView) findViewById(R.id.img_option);
        option = (RelativeLayout) findViewById(R.id.option);
        text_address = (TextView) findViewById(R.id.text_address);
        text_comment = (TextView) findViewById(R.id.text_comment);
        text_like = (TextView) findViewById(R.id.text_like);

        divider = (ImageView) findViewById(R.id.divider);

        img_like = (ImageView) findViewById(R.id.img_like);

        image_user.setOnClickListener(this);
        btn_user.setOnClickListener(this);
        btn_culture_detail.setOnClickListener(this);
        text_username.setOnClickListener(this);
        text_uploadtime.setOnClickListener(this);
        image_fee.setOnClickListener(this);
        image_ing.setOnClickListener(this);
        text_title.setOnClickListener(this);
        image_main.setOnClickListener(this);
        text_date.setOnClickListener(this);
        text_time.setOnClickListener(this);
        like.setOnClickListener(this);
        comment.setOnClickListener(this);
        img_tag.setOnClickListener(this);
        option.setOnClickListener(this);
        img_option.setOnClickListener(this);

        img_like.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_culture_detail:
            case R.id.image_main: {
                Intent mIntent = new Intent(getContext(), CultureDetailActivity.class);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData.sContentKey);
                getContext().startActivity(mIntent);
                Toast.makeText(getContext(), "선택부분 click", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_user:
            case R.id.text_username:
            case R.id.image_user: {
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                if(DefineContentType.BLOG_TYPE_MYBLOG == mData.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                }else{
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                getContext().startActivity(mIntent);
                break;
            }
            case R.id.comment: {
                Intent mIntent = new Intent(getContext(), CommentActivity.class);
                mIntent.putExtra(DefineNetwork.CONTENT_KEY, mData.sContentKey);
                getContext().startActivity(mIntent);
                break;
            }

            case R.id.option:
            case R.id.img_option: {
                onOptionPopupWindow(v);
                break;
            }

            case R.id.like:
            case R.id.img_like: {
                if(img_like.isSelected()){
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "unlike"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(getContext(), "좋아요가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(text_like.getText().toString());
                            text_like.setText(nCount-1+"");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(getContext(), "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "like"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(getContext(), "좋아요", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(text_like.getText().toString());
                            text_like.setText(nCount + 1 + "");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(getContext(), "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                img_like.setSelected(!img_like.isSelected());
                break;
            }

        }
    }


    public void setItemData(CultureItemData data) {
        mData = data;
        String user = PropertyManager.getInstance().mUser.sBlogKey;
        if(mData.arrLikes.contains(user)){
            img_like.setSelected(true);
        }

        //ImageLoader.getInstance().displayImage(data.mUserData.thProfile, image_user);
        Picasso.with(getContext()).load(data.mUserData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(image_user);
        if(0 < data.arrIMGs.size()) {
            Picasso.with(getContext()).load(data.arrIMGs.get(0)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(getContext().getResources().getDisplayMetrics().widthPixels,0).into(image_main);
           // ImageLoader.getInstance().displayImage(data.arrIMGs.get(0), image_main);
        }

        if(data.nFee > 0){
            image_fee.setImageResource(R.drawable.fee_charged);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.fee_charged, image_fee);
        }else{
            image_fee.setImageResource(R.drawable.fee_free);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.fee_free, image_fee);
        }
        //if(DateManager.getInstance().isEnd(data.dEndDate+data.dEndTime)){
        if(DateManager.getInstance().isEnd("2015.11.11 17:30")){ //test
            image_ing.setImageResource(R.drawable.peformance_end);
           // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.peformance_end, image_ing);
        }else{
            image_ing.setImageResource(R.drawable.performance_ing);
           // ImageLoader.getInstance().displayImage("drawable://" + R.drawable.performance_ing, image_ing);
        }

        text_username.setText(data.mUserData.sArtist);
        text_title.setText(data.sTitle);
        text_uploadtime.setText(DateManager.getInstance().getPastTime(data.sWriteDate));
        text_date.setText(data.dStartDate);
        text_time.setText(data.dStartTime);
        text_address.setText(data.sAddress);
        text_comment.setText(data.nCommentCount+"");
        text_like.setText(data.nLikeCount+"");




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


}
