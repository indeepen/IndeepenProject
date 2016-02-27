package com.release.indeepen.culture;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
import com.release.indeepen.management.networkManager.netArt.ArtController;
import com.release.indeepen.management.networkManager.netArt.DELETEContentRequest;
import com.release.indeepen.management.networkManager.netArt.data.ContentResult;
import com.release.indeepen.management.networkManager.netCommon.CommonController;
import com.release.indeepen.management.networkManager.netCommon.PUTLikeRequest;
import com.release.indeepen.management.networkManager.netCulture.CultureController;
import com.release.indeepen.management.networkManager.netCulture.CultureDetailRequest;
import com.squareup.picasso.Picasso;


public class CultureDetailActivity extends AppCompatActivity implements View.OnClickListener,OptionPopupWindow.OptionClickListener {

    FrameLayout container;
    ImageView imageView;
    Boolean tag = false;

    Button btn;
    ImageButton btn_map;
    LinearLayout comment;
    RelativeLayout option;
    ImageView img_tag, img_like, image_main, image_user, image_fee, image_ing, img_01, img_02, img_03, img_04, img_05;
    TextView text_username, text_title, text_uploadtime, text_date, text_time, text_address, text_comment, text_like, text_content, text_fee, textView19;

    String sContentKey;
    CultureItemData mData;
    ImageView[] arrImgs;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);


        image_main = (ImageView) findViewById(R.id.img_main);

        container = (FrameLayout) findViewById(R.id.container_map);


        //  RelativeLayout mapView = new RelativeLayout();

        final LinearLayout show_map = (LinearLayout) findViewById(R.id.show_map);
        btn_map = (ImageButton) findViewById(R.id.btn_show_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    show_map.setVisibility(View.VISIBLE);
                    tag = true;
                } else {
                    show_map.setVisibility(View.GONE);
                    tag = false;
                }
            }
        });


        imageView = (ImageView) findViewById(R.id.img_main);
        comment = (LinearLayout) findViewById(R.id.comment);
        img_tag = (ImageView) findViewById(R.id.img_tag);
        option = (RelativeLayout) findViewById(R.id.option);
        img_like = (ImageView) findViewById(R.id.img_like);
        image_user = (ImageView) findViewById(R.id.image_user);
        image_fee = (ImageView) findViewById(R.id.imageView8);
        image_ing = (ImageView) findViewById(R.id.image_ing);
        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        img_03 = (ImageView) findViewById(R.id.img_03);
        img_04 = (ImageView) findViewById(R.id.img_04);
        img_05 = (ImageView) findViewById(R.id.img_05);

        text_username = (TextView) findViewById(R.id.text_username);
        text_title = (TextView) findViewById(R.id.text_title);
        text_uploadtime = (TextView) findViewById(R.id.text_uploadtime);
        text_date = (TextView) findViewById(R.id.text_comm_date);
        text_time = (TextView) findViewById(R.id.text_time);
        text_address = (TextView) findViewById(R.id.text_address);
        text_comment = (TextView) findViewById(R.id.textView19);
        text_like = (TextView) findViewById(R.id.textView18);
        text_fee = (TextView) findViewById(R.id.text_fee);
        text_content = (TextView) findViewById(R.id.text_content);


        option.setOnClickListener(this);


        arrImgs = new ImageView[]{img_01, img_02, img_03, img_04, img_05};


        init();

    }

    private void init() {
        if (null != getIntent()) {
            sContentKey = getIntent().getStringExtra(DefineContentType.BUNDLE_DATA_REQUEST);

            if (TextUtils.isEmpty(sContentKey)) finish();

            CultureDetailRequest request = new CultureDetailRequest();
            request.setURL(DefineNetwork.CULTURE_CONTENT + sContentKey);
            CultureController.getInstance().getDetailContent(request, new NetworkProcess.OnResultListener<ContentResult>() {
                @Override
                public void onSuccess(NetworkRequest<ContentResult> request, ContentResult result) {
                    mData = (CultureItemData) ArtController.getInstance().getContent(result.mResult);
                    setData(mData);
                }

                @Override
                public void onFail(NetworkRequest<ContentResult> request, int code) {

                }
            });
        }
    }

    private void setData(CultureItemData data) {


        mData = data;
        // ImageLoader.getInstance().displayImage(data.mUserData.thProfile, image_user);
        img_like.setSelected(false);
        String user = PropertyManager.getInstance().mUser.sBlogKey;
        if(mData.arrLikes.contains(user)){
            img_like.setSelected(true);
        }
        Picasso.with(this).load(data.mUserData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(image_user);

        if (0 < data.arrIMGs.size()) {
            int idx =0 ;
            for(String img : data.arrIMGs) {
                //   arrImgs[idx].setVisibility(View.GONE);
                arrImgs[idx].setVisibility(View.VISIBLE);
                if(idx == 0) {
                    Picasso.with(this).load(img).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);
                    //ImageLoader.getInstance().displayImage(img, image_main);
                }
                Picasso.with(this).load(img).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(arrImgs[idx]);
                //ImageLoader.getInstance().displayImage(img,  arrImgs[idx]);
                idx++;
            }
        }

        arrImgs[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(0)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CultureDetailActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);
            }
        });
        arrImgs[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(1)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CultureDetailActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);

            }
        });
        arrImgs[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(2)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CultureDetailActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);

            }
        });
        arrImgs[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(3)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CultureDetailActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);

            }
        });
        arrImgs[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(4)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CultureDetailActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(image_main);

            }
        });


        if (data.nFee > 0) {
            image_fee.setImageResource(R.drawable.fee_charged);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.fee_charged, image_fee);
        } else {
            image_fee.setImageResource(R.drawable.fee_free);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.fee_free, image_fee);
        }
        if(DateManager.getInstance().isEnd(data.dEndDate+" "+data.dEndTime)){
            //if (DateManager.getInstance().isEnd("2015.11.11 17:30")) { //test
            image_ing.setImageResource(R.drawable.peformance_end);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.peformance_end, image_ing);
        } else {
            image_ing.setImageResource(R.drawable.performance_ing);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.performance_ing, image_ing);
        }

        text_content.setText(data.sText);
        text_username.setText(data.mUserData.sArtist);
        text_title.setText(data.sTitle);
        text_uploadtime.setText(DateManager.getInstance().getPastTime(data.sWriteDate));
        text_date.setText("날짜 : "+data.dStartDate+"~"+data.dEndDate);
        text_time.setText("시간 : "+data.dStartTime+"~"+data.dEndTime);
        text_address.setText("주소 : "+data.sAddress);
        text_comment.setText(data.nCommentCount + "");
        text_like.setText(data.nLikeCount + "");
        text_fee.setText("입장료 : "+data.nFee+"원");


        /*for(i = 0 ; i <mData.arrIMGs.size(); i++){
            arrImgs[i].setVisibility(View.VISIBLE);
            Picasso.with(CultureDetailActivity.this).load(mData.arrIMGs.get(i)).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(arrImgs[i]);
            arrImgs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 큰이미지에다가 받아온 이미지배열.get(i) --> url 배치
                  Picasso.with(CultureDetailActivity.this).load().placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(image_main);

                }
            });
        }*/

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user:
            case R.id.text_username:
            case R.id.image_user: {
                Intent mIntent = new Intent(this, MainActivity.class);
                mIntent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TYPE_ON_NEW_REPLACE);
                mIntent.putExtra(DefineNetwork.BLOG_KEY, mData.sBlogKey);
                if(DefineContentType.BLOG_TYPE_MYBLOG == mData.nBlogType) {
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_BLOG);
                }else{
                    mIntent.putExtra(DefineContentType.KEY_ON_NEW_WHERE, DefineContentType.TO_SPACE);
                }
                this.startActivity(mIntent);
                break;
            }
            case R.id.comment: {
                Intent mIntent = new Intent(this, CommentActivity.class);
                mIntent.putExtra(DefineNetwork.CONTENT_KEY, mData.sContentKey);
                mIntent.putExtra(DefineNetwork.CONTENT_DATA, mData);
                this.startActivity(mIntent);
                break;
            }

            case R.id.option: {
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
                            Toast.makeText(CultureDetailActivity.this, "좋아요가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(text_like.getText().toString());
                            text_like.setText(nCount-1+"");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(CultureDetailActivity.this, "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    PUTLikeRequest request = new PUTLikeRequest();
                    request.setURL(String.format(DefineNetwork.LIKE, mData.sContentKey, "like"));

                    CommonController.getInstance().like(request, new NetworkProcess.OnResultListener<String>() {
                        @Override
                        public void onSuccess(NetworkRequest<String> request, String result) {
                            Toast.makeText(CultureDetailActivity.this, "좋아요", Toast.LENGTH_SHORT).show();
                            int nCount = Integer.parseInt(text_like.getText().toString());
                            text_like.setText(nCount + 1 + "");
                        }

                        @Override
                        public void onFail(NetworkRequest<String> request, int code) {
                            Toast.makeText(CultureDetailActivity.this, "잠시 후 다시 시도해 주세요. "+code, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                img_like.setSelected(!img_like.isSelected());
                break;

            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            switch (item.getItemId()) {
                case android.R.id.home:
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    @Override
    public void onOptionClickEvent(int mode) {
        switch (mode){
            case OptionPopupWindow.UNLIKE:{
                break;
            }
            case OptionPopupWindow.SHARE:{
                Toast.makeText(CultureDetailActivity.this, "준비중입니다", Toast.LENGTH_SHORT).show();

                break;
            }
            case OptionPopupWindow.EDIT:{
                Toast.makeText(CultureDetailActivity.this, "준비중입니다", Toast.LENGTH_SHORT).show();

                break;
            }
            case OptionPopupWindow.DELETE:{
                DELETEContentRequest request = new DELETEContentRequest();
                request.setURL(String.format(DefineNetwork.DELETE_CONTENT, mData.sContentKey));
                CommonController.getInstance().deleteContent(request, new NetworkProcess.OnResultListener<String>() {
                    @Override
                    public void onSuccess(NetworkRequest<String> request, String result) {
                        Toast.makeText(CultureDetailActivity.this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(NetworkRequest<String> request, int code) {
                        Toast.makeText(CultureDetailActivity.this, code + "- error", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }

    private void onOptionPopupWindow(View view) {

        OptionPopupWindow popup = new OptionPopupWindow(this);
        popup.setOnOptionClick(this);
        popup.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popup.showAsDropDown(comment, Gravity.RELATIVE_LAYOUT_DIRECTION, 16, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

}