package com.release.indeepen.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.MainActivity;
import com.release.indeepen.R;
import com.release.indeepen.create.selectMedia.MediaMultiChoiceActivity;
import com.release.indeepen.create.selectMedia.MusicChoiceActivity;
import com.release.indeepen.youtube.uploadManager.YoutubeUploadActivity;

public class CreateActivity extends AppCompatActivity {
    Boolean tag = false;
    LinearLayout btn_create_music_add;
    ImageView btn_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ImageView btn = (ImageView) findViewById(R.id.btn_create_image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MediaMultiChoiceActivity.class);
                intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_PAINT);
                startActivity(intent);
            }
        });

        btn = (ImageView) findViewById(R.id.btn_create_picture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MediaMultiChoiceActivity.class);
                intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_PICTURE);
                startActivity(intent);
            }
        });

        btn_create_music_add = (LinearLayout) findViewById(R.id.btn_create_music_add);
        btn_music  = (ImageView) findViewById(R.id.btn_create_music);
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tag == false) {
                    btn_create_music_add.setVisibility(View.VISIBLE);
                    btn_music.setVisibility(View.GONE);

                    Button btn = (Button) findViewById(R.id.btn_music);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CreateActivity.this, MusicChoiceActivity.class);
                            intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_MUSIC);
                            startActivity(intent);
                        }
                    });

                    btn = (Button) findViewById(R.id.btn_photo);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CreateActivity.this, MediaMultiChoiceActivity.class);
                            intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE);
                            startActivity(intent);
                        }
                    });

                    btn = (Button) findViewById(R.id.btn_video);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CreateActivity.this, YoutubeUploadActivity.class);
                            intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO);
                            startActivity(intent);
                        }
                    });


                    tag = true;
                } else {
                    btn_create_music_add.setVisibility(View.GONE);
                    btn_music.setVisibility(View.VISIBLE);
                    tag = false;
                }
                tag = false;
            }
        });

        btn = (ImageView) findViewById(R.id.btn_create_media);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, YoutubeUploadActivity.class);
                intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_YOUTUBE);
                startActivity(intent);
            }
        });

        btn = (ImageView) findViewById(R.id.btn_create_culture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, MediaMultiChoiceActivity.class);
                intent.putExtra(DefineContentType.BUNDLE_DATA_TYPE, DefineContentType.SINGLE_ART_TYPE_CULTURE);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        btn_music.setVisibility(View.VISIBLE);
        btn_create_music_add.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        //       super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DefineContentType.KEY_ON_NEW_REQUEST, DefineContentType.TO_CULTURE_TAB);
        startActivity(intent);
        finish();
    }

}
