package com.release.indeepen.create.selectMedia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.DefineNetwork;
import com.release.indeepen.MapActivity;
import com.release.indeepen.R;
import com.release.indeepen.calendar.CalendarFragment;
import com.release.indeepen.calendar.CalendarItem;
import com.release.indeepen.calendar.CalendarItemView;
import com.release.indeepen.content.art.ContentImageData;
import com.release.indeepen.content.art.detail.SimpleImageAdapter;
import com.release.indeepen.create.BlogListAdapter;
import com.release.indeepen.create.HorizontalListView;
import com.release.indeepen.culture.CultureItemData;
import com.release.indeepen.login.PropertyManager;
import com.release.indeepen.management.dateManager.DateManager;
import com.release.indeepen.management.networkManager.NetworkProcess;
import com.release.indeepen.management.networkManager.NetworkRequest;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogController;
import com.release.indeepen.management.networkManager.netMyBlog.MyBlogInfoListRequest;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfo;
import com.release.indeepen.management.networkManager.netMyBlog.data.BlogInfoListResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateInputCultureActivity extends AppCompatActivity {


    ImageView img_main, img_01, img_02, img_03, img_04, img_05;
    ImageView showName, showType, showDate, showTime, showLocation, showFee, vImgBlog;
    int count = 0;
    int countNum = 2;
    TextView vTextBlog;

    Boolean tag = false;
    Boolean tag_fee = false;

    public HorizontalListView vList;
    BlogListAdapter mAdapter;
    CultureItemData mCreateData;
    SimpleImageAdapter mContentAdapter;
    ImageView[] arrImgs;
    CalendarFragment fCalendar;

    String date1;//+ item.year + "." + (item.month + 1) + "." + item.dayOfMonth;
    String date2; //+ item.year + "." + (item.month + 1) + "." + item.dayOfMonth;
    long ndate1;
    long ndate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_input_culture);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setTitle("문화예술 업로드");

        mCreateData = (CultureItemData) getIntent().getSerializableExtra(DefineContentType.BUNDLE_DATA_REQUEST);
        mContentAdapter = new SimpleImageAdapter();
        //vList.setAdapter(mAdapter);

        if (null == mCreateData) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateInputCultureActivity.this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("올바른 접근이 아닙니다");
            builder.setNeutralButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MediaMultiChoiceActivity.mediaMultiChoiceActivity.finish();
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();

        }

        img_main = (ImageView) findViewById(R.id.img_main);

        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        img_03 = (ImageView) findViewById(R.id.img_03);
        img_04 = (ImageView) findViewById(R.id.img_04);
        img_05 = (ImageView) findViewById(R.id.img_05);

        showName = (ImageView) findViewById(R.id.btn_show_name);
        showType = (ImageView) findViewById(R.id.btn_show_type);
        showDate = (ImageView) findViewById(R.id.btn_show_date);
        showTime = (ImageView) findViewById(R.id.btn_show_time);
        showLocation = (ImageView) findViewById(R.id.btn_show_location);
        showFee = (ImageView) findViewById(R.id.btn_show_fee);

        arrImgs = new ImageView[]{img_01, img_02, img_03, img_04, img_05};


        ImageButton btn_tag = (ImageButton) findViewById(R.id.btn_tag);
        btn_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateInputCultureActivity.this, CreateInputCultureTag.class);
                startActivity(intent);
            }
        });


        //  Boolean show = false;

        final LinearLayout setName = (LinearLayout) findViewById(R.id.set_name);
        RelativeLayout name = (RelativeLayout) findViewById(R.id.show_set_name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setName.setVisibility(View.VISIBLE);
                    showName.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setName.setVisibility(View.GONE);
                    showName.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

            }
        });
        final RadioGroup radioType = (RadioGroup) findViewById(R.id.radio_type);
        final RadioButton all, display, performance, show, art, festival;

        display = (RadioButton) findViewById(R.id.img_display);
        performance = (RadioButton) findViewById(R.id.img_performance);
        show = (RadioButton) findViewById(R.id.img_show);
        art = (RadioButton) findViewById(R.id.img_art);
        festival = (RadioButton) findViewById(R.id.img_festival);
        radioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.img_display: {
                        mCreateData.nCultureType = DefineContentType.CULTURE_EXHIBITION;
                        break;
                    }
                    case R.id.img_performance: {
                        mCreateData.nCultureType = DefineContentType.CULTURE_PERFORMANCE;
                        break;
                    }
                    case R.id.img_show: {
                        mCreateData.nCultureType = DefineContentType.CULTURE_SHOW;
                        break;
                    }
                    case R.id.img_art: {
                        mCreateData.nCultureType = DefineContentType.CULTURE_ART_MEETING;
                        break;
                    }
                    case R.id.img_festival: {
                        mCreateData.nCultureType = DefineContentType.CULTURE_FESTIVAL;
                        break;
                    }
                }
            }
        });

        final LinearLayout setType = (LinearLayout) findViewById(R.id.set_type);
        RelativeLayout type = (RelativeLayout) findViewById(R.id.show_set_type);
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setType.setVisibility(View.VISIBLE);
                    showType.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setType.setVisibility(View.GONE);
                    showType.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

            }
        });

        final RelativeLayout setDate = (RelativeLayout) findViewById(R.id.set_date);
        RelativeLayout date = (RelativeLayout) findViewById(R.id.show_set_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setDate.setVisibility(View.VISIBLE);
                    showDate.setImageResource(R.drawable.btn_top);
                    onCalendar(v);
                    tag = true;
                } else {
                    setDate.setVisibility(View.GONE);
                    showDate.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

            }
        });

        fCalendar = new CalendarFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, fCalendar).commit();


        RelativeLayout location = (RelativeLayout) findViewById(R.id.show_set_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateInputCultureActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


        final LinearLayout setTime = (LinearLayout) findViewById(R.id.set_time);
        RelativeLayout time = (RelativeLayout) findViewById(R.id.show_set_time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setTime.setVisibility(View.VISIBLE);
                    showTime.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setTime.setVisibility(View.GONE);
                    showTime.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

            }
        });


        final LinearLayout setFee = (LinearLayout) findViewById(R.id.set_fee);
        final RelativeLayout show_input_fee = (RelativeLayout) findViewById(R.id.show_input_fee);


        RelativeLayout fee = (RelativeLayout) findViewById(R.id.show_set_fee);
        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setFee.setVisibility(View.VISIBLE);
                    showFee.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setFee.setVisibility(View.GONE);
                    showFee.setImageResource(R.drawable.btn_bottom);
                    show_input_fee.setVisibility(View.GONE);
                    tag = false;
                }

            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.fee_group);
        final RadioButton rFee, rFree;
        rFree = (RadioButton) findViewById(R.id.radio_free);
        rFee = (RadioButton) findViewById(R.id.radio_fee);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio_fee:
                        show_input_fee.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radio_free:
                        show_input_fee.setVisibility(View.GONE);
                        break;
                }
            }
        });


        vList = (HorizontalListView) findViewById(R.id.list_create_header_blog);
        mAdapter = new BlogListAdapter();
        vList.setAdapter(mAdapter);

        vTextBlog = (TextView) findViewById(R.id.imageView27);
        vImgBlog = (ImageView) findViewById(R.id.imageView);

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (tag == false) {
                    setName.setVisibility(View.VISIBLE);
                    showName.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setName.setVisibility(View.GONE);
                    showName.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

                BlogInfo mBlogData = (BlogInfo) mAdapter.getItem(position);
                mCreateData.sBlogKey = mBlogData.sBlogKey;

                vTextBlog.setText(mBlogData.sArtist);
                if (!TextUtils.isEmpty(mBlogData.thProfile)) {
                    // ImageLoader.getInstance().displayImage(mBlogData.sIMGPath, vImgBlog);
                    Picasso.with(CreateInputCultureActivity.this).load(mBlogData.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                }
            }
        });
        setBlogs();
        init();

    }


    private void init() {
        // mCreateData = (CultureItemData) getIntent().getSerializableExtra(DefineContentType.BUNDLE_DATA_REQUEST);
        // setBlogs();
        if (0 < mCreateData.arrIMGs.size()) {
            int idx = 0;
            for (String path : mCreateData.arrIMGs) {
                final Uri uri = Uri.fromFile(new File(path));
                arrImgs[idx].setVisibility(View.VISIBLE);
                if (idx == 0) {
                    Picasso.with(this).load(uri).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);
                    //ImageLoader.getInstance().displayImage(img, image_main);
                }
                Picasso.with(this).load(uri).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).into(arrImgs[idx]);
                //ImageLoader.getInstance().displayImage(img,  arrImgs[idx]);
                idx++;
            }
        }
        //mContentAdapter.add(uri.toString());
        //  Picasso.with(CreateInputCultureActivity.this).load(uri).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(arrImgs);
        arrImgs[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CreateInputCultureActivity.this).load(Uri.fromFile(new File(mCreateData.arrIMGs.get(0)))).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);

            }
        });
        arrImgs[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CreateInputCultureActivity.this).load(Uri.fromFile(new File(mCreateData.arrIMGs.get(1)))).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);

            }
        });
        arrImgs[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CreateInputCultureActivity.this).load(Uri.fromFile(new File(mCreateData.arrIMGs.get(2)))).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);

            }
        });
        arrImgs[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CreateInputCultureActivity.this).load(Uri.fromFile(new File(mCreateData.arrIMGs.get(3)))).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);

            }
        });
        arrImgs[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(CreateInputCultureActivity.this).load(Uri.fromFile(new File(mCreateData.arrIMGs.get(4)))).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).resize(CreateInputCultureActivity.this.getResources().getDisplayMetrics().widthPixels, 0).into(img_main);

            }
        });
    }


    private void setBlogs() {

        MyBlogInfoListRequest request = new MyBlogInfoListRequest();
        request.setURL(DefineNetwork.MY_BLOG_INFO_LIST);

        MyBlogController.getInstance().getMyBlogInfoList(request, new NetworkProcess.OnResultListener<BlogInfoListResult>() {
            @Override
            public void onSuccess(NetworkRequest<BlogInfoListResult> request, BlogInfoListResult result) {
                //popupSelectBlog.setData(result.arrBlogs);
                mAdapter.addList(result.arrBlogs);
                for (BlogInfo blog : result.arrBlogs) {
                    if (blog.isActivated) {
                        mCreateData.sBlogKey = blog.sBlogKey;
                        mCreateData.mUserData.sArtist = blog.sArtist;
                        Picasso.with(CreateInputCultureActivity.this).load(blog.thProfile).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                        vTextBlog.setText(blog.sArtist);
                        break;
                    }
                }
            }

            @Override
            public void onFail(NetworkRequest<BlogInfoListResult> request, int code) {
                mCreateData.sBlogKey = PropertyManager.getInstance().mUser.sActiveBlogKey;
                //Picasso.with(CreateImageContentActivity.this).load(R.drawable.backsample).placeholder(R.drawable.ic_empty).error(R.drawable.ic_error).fit().into(vImgBlog);
                //vTextBlog.setText("");
                Toast.makeText(CreateInputCultureActivity.this, "블로그 정보를 불러오는데 실패 하였습니다. 현재 활동 중인 블로그에 글이 게시 됩니다. -" + code, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onCalendar(View view) {

        fCalendar.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CalendarItem item = (CalendarItem) fCalendar.mAdapter.getItem(position);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                if (item.inMonth) {
                    if (item.isChecked) {
                        //((CalendarItemView)view).setBackgroundColor(Color.TRANSPARENT);
                        item.isChecked = false;
                        count--;
                    } else {
                        if (count >= countNum) {
                            Toast.makeText(CreateInputCultureActivity.this, "시작날짜와 종료날짜를 선택하세요", Toast.LENGTH_SHORT).show();
                        } else {
                            item.isChecked = true;
                            if (0 == count) {
                                date1 = item.year + "." + (item.month + 1) + "." + item.dayOfMonth + "";
                                Date date = null;
                                try {
                                    date = sdf.parse(date1);
                                    ndate1 =  date.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if (1 == count) {
                                date2 = item.year + "." + (item.month + 1) + "." + item.dayOfMonth + "";
                                Date date = null;
                                try {
                                    date = sdf.parse(date2);
                                    ndate2 =  date.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if (ndate1 > ndate2) {
                                    mCreateData.dStartDate = date2;
                                    mCreateData.dEndDate = date1;
                                } else {
                                    mCreateData.dStartDate = date1;
                                    mCreateData.dEndDate = date2;
                                }
                            }

                            Log.e("달력디버", "start:" + mCreateData.dStartDate + "/ end:" + mCreateData.dEndDate);
                            //((CalendarItemView) view).setBackgroundResource(R.drawable.calendar_selected);
                            count++;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                super.onBackPressed();
                break;
            }
            case R.id.action_create: {
                Toast.makeText(getApplicationContext(), "서비스 준비중입니다", Toast.LENGTH_SHORT).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}