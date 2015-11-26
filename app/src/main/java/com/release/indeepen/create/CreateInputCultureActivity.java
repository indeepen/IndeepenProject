package com.release.indeepen.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.release.indeepen.R;

public class CreateInputCultureActivity extends AppCompatActivity {


    ImageView img_main, img_01, img_02, img_03, img_04, img_05;
    ImageButton showName, showType, showDate, showTime, showLocation, showFee;
    Boolean tag = false;
    Boolean tag_fee = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_input_culture);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("문화예술 업로드");


        img_main = (ImageView) findViewById(R.id.img_main);

        img_01 = (ImageView) findViewById(R.id.img_01);
        img_02 = (ImageView) findViewById(R.id.img_02);
        img_03 = (ImageView) findViewById(R.id.img_03);
        img_04 = (ImageView) findViewById(R.id.img_04);
        img_05 = (ImageView) findViewById(R.id.img_05);

        showName = (ImageButton) findViewById(R.id.btn_show_name);
        showType = (ImageButton) findViewById(R.id.btn_show_type);
        showDate = (ImageButton) findViewById(R.id.btn_show_date);
        showTime = (ImageButton) findViewById(R.id.btn_show_time);
        showLocation = (ImageButton) findViewById(R.id.btn_show_location);
        showFee = (ImageButton) findViewById(R.id.btn_show_fee);

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

        final LinearLayout setDate = (LinearLayout) findViewById(R.id.set_date);
        RelativeLayout date = (RelativeLayout) findViewById(R.id.show_set_date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == false) {
                    setDate.setVisibility(View.VISIBLE);
                    showDate.setImageResource(R.drawable.btn_top);
                    tag = true;
                } else {
                    setDate.setVisibility(View.GONE);
                    showDate.setImageResource(R.drawable.btn_bottom);
                    tag = false;
                }

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
                    tag = false;
                }

            }
        });

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.fee_group);
        final RelativeLayout show_input_fee = (RelativeLayout) findViewById(R.id.show_input_fee);


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

}

