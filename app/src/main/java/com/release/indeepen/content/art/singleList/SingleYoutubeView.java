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

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.release.indeepen.comment.CommentActivity;
import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.content.OptionView;
import com.release.indeepen.content.art.ContentYoutubeData;
import com.release.indeepen.content.art.detail.ContentDetailActivity;
import com.release.indeepen.culture.OptionPopupWindow;
import com.release.indeepen.fan.FanMainFragment;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netArt.DELETEContentRequest;
import com.release.indeepen.management.networkManager.netCommon.CommonController;
import com.release.indeepen.management.networkManager.netCommon.PUTLikeRequest;
import com.squareup.picasso.Picasso;

/**
 * Created by lyo on 2015-11-05.
 */
public class SingleYoutubeView  extends LinearLayout implements View.OnClickListener, OptionPopupWindow.OptionClickListener, OptionView{

    LinearLayout like, comment;
    RelativeLayout option;
    ImageView vImg_like, vImg_comment, vImg_option;
    ImageView vThPro, vIMGEmotion;
    TextView  vTextArtist, vTextDate, vText, vTextLike, vTextComm/*, vTextOption, vTextCommUser1, vTextCommUser2, vTextCommCon1, vTextCommCon2;*/;
    ContentYoutubeData mData;
    SingleHeaderView vHeader;
    SingleFooterView vFooter;
    ImageView vYoutub;
    OptionPopupWindow popup;
    public SingleYoutubeView(Context context) {
        super(context);
        init();
    }

    public SingleYoutubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.view_single_youtube, this);

        vHeader = (SingleHeaderView) findViewById(R.id.view_single_youtube_header);
        vFooter = (SingleFooterView) findViewById(R.id.view_single_youtube_footer);

        vThPro = (ImageView) vHeader.findViewById(R.id.img_image_single_thpro);
        vIMGEmotion = (ImageView) vHeader.findViewById(R.id.img_image_single_emotion);
        vTextArtist = (TextView) vHeader.findViewById(R.id.text_image_single_artist);
        vTextDate = (TextView) vHeader.findViewById(R.id.text_image_single_date);
        vText = (TextView) vHeader.findViewById(R.id.text_image_single_text);

        vThPro.setOnClickListener(this);
        vTextArtist.setOnClickListener(this);
        vTextDate.setOnClickListener(this);
        vText.setOnClickListener(this);

        //vYoutub = (YouTubeThumbnailView) findViewById(R.id.youtub_single);
        vYoutub = (ImageView) findViewById(R.id.youtub_single);
       // vYoutub.initialize(DeveloperKey.DEVELOPER_KEY, this);
        vYoutub.setOnClickListener(this);

        vTextLike = (TextView) vFooter.findViewById(R.id.text_image_single_like);
        vTextComm = (TextView) vFooter.findViewById(R.id.text_image_single_comm);
      /*  vTextCommUser1 = (TextView) vFooter.findViewById(R.id.text_image_single_nick1);
        vTextCommUser2 = (TextView) vFooter.findViewById(R.id.text_image_single_nick2);
        vTextCommCon1 = (TextView) vFooter.findViewById(R.id.text_image_single_comm1);
        vTextCommCon2 = (TextView) vFooter.findViewById(R.id.text_image_single_comm2);

        vTextCommUser1.setOnClickListener(this);
        vTextCommUser2.setOnClickListener(this);
        vTextCommCon1.setOnClickListener(this);
        vTextCommCon2.setOnClickListener(this);*/
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

    /*public YouTubeThumbnailView getYoutube(){
        return vYoutub;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.youtub_single:
            case R.id.text_image_single_text:
            case R.id.img_image_single_emotion:
         /*   case R.id.text_image_single_nick1:
            case R.id.text_image_single_nick2:
            case R.id.text_image_single_comm1:
            case R.id.text_image_single_comm2: */{
                Intent mIntent = new Intent(getContext(), ContentDetailActivity.class);
               // mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, mData.sContentKey);
                mIntent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, mData.nArtType);
                getContext().startActivity(mIntent);
                break;
            }
            case R.id.text_image_single_artist:
            case R.id.img_image_single_thpro: {
                Intent mIntent = new Intent(getContext(), MainActivity.class);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
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
                if(vImg_like.isSelected()){
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "unlike"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(getContext(), "좋아요가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(vTextLike.getText().toString());
                            vTextLike.setText(nCount-1+"");
                            mData.nLikeCount -= 1;
                            for (int idx = 0; idx < mData.arrLikes.size(); idx++) {
                                if (PropertyManager.getInstance().mUser.sBlogKey.equals(mData.arrLikes.get(idx))) {
                                    mData.arrLikes.remove(idx);
                                    break;
                                }
                            }
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
                            int nCount = Integer.parseInt(vTextLike.getText().toString());
                            vTextLike.setText(nCount + 1 + "");
                            mData.nLikeCount += 1;
                            mData.arrLikes.add(PropertyManager.getInstance().mUser.sBlogKey);
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(getContext(), "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                vImg_like.setSelected(!vTextLike.isSelected());
                break;
            }
            case R.id.comment:
            case R.id.image_single_comm:
            case R.id.text_image_single_comm: {
                Intent mIntent = new Intent(getContext(), CommentActivity.class);
                mIntent.putExtra(DefineNetwork.CONTENT_KEY, mData.sContentKey);
                mIntent.putExtra(DefineNetwork.CONTENT_DATA, mData);
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
    public void onOptionClickEvent(int mode) {
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
                        mFanMainFragment.setChageData(DefineContentType.DELETE, nPosition, null );

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
        popup = new OptionPopupWindow(getContext());
        popup.setOnOptionClick(this);
        popup.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popup.showAsDropDown(comment, Gravity.RELATIVE_LAYOUT_DIRECTION , 16 , 0);
        }
        popup.setFocusable(true);
        setFocusable(true);
    }
    FanMainFragment mFanMainFragment;
    int nPosition;

    public void setData(ContentYoutubeData data,  FanMainFragment fanMainFragment, int position) {
        if (null == data) return;
        mFanMainFragment = fanMainFragment;
        nPosition = position;
        mData = data;
        vImg_like.setSelected(false);
        Picasso.with(getContext()).load("https://i1.ytimg.com/vi/" + mData.sYouTubePath + "/mqdefault.jpg").placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(getContext().getResources().getDisplayMetrics().widthPixels, 0).into(vYoutub);

        String user = PropertyManager.getInstance().mUser.sBlogKey;
        if(mData.arrLikes.contains(user)){
            vImg_like.setSelected(true);
        }
        Picasso.with(getContext()).load(mData.mUserData.thProfile).fit().into(vThPro);
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

    }


    @Override
    public boolean closePopup() {
        if(null != popup && popup.isShowing()) {
            popup.dismiss();
            return true;
        }
        return false;
    }


}
