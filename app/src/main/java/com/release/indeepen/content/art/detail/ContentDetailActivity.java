package com.release.indeepen.content.art.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.release.indeepen.DefineContentType;
import com.release.indeepen.R;
import com.release.indeepen.content.ContentData;
import com.release.indeepen.culture.CultureDetailActivity;
import com.release.indeepen.management.musicManager.MusicManager;

public class ContentDetailActivity extends AppCompatActivity {


    Fragment vfragment;
    ContentData mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);

        Intent mIntent = getIntent();

        switch(mIntent.getIntExtra(DefineContentType.BUNDLE_DATA_TYPE, -1)){
            case DefineContentType.SINGLE_ART_TYPE_PAINT :
            case DefineContentType.SINGLE_ART_TYPE_PICTURE :
            case DefineContentType.SINGLE_ART_TYPE_MUSIC_PICTURE :{
                vfragment = new ContentDetailImageFragment();
                break;
            }
           /* case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO :{
                vfragment = new ContentDetailVideoFragment();
                break;
            }*/
            case DefineContentType.SINGLE_ART_TYPE_MUSIC:{
               vfragment = new ContentDetailMusicFragment();
                break;
            }
            case DefineContentType.SINGLE_ART_TYPE_YOUTUBE:
            case DefineContentType.SINGLE_ART_TYPE_MUSIC_VIDEO:{
                vfragment = new ContentDetailYoutubeFragment();
                break;
            }
            case DefineContentType.SINGLE_ART_TYPE_CULTURE :{
                Intent intent = new Intent(this, CultureDetailActivity.class);
                intent.putExtra(DefineContentType.BUNDLE_DATA_REQUEST, getIntent().getStringExtra(DefineContentType.BUNDLE_DATA_REQUEST));
                startActivity(intent);
                break;
            }

        }
        if(null !=  vfragment) {
            vfragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.container_single_content, vfragment).commit();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if( MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()){
            MusicManager.getMusicManager().pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()){
            MusicManager.getMusicManager().pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if( MusicManager.PlayState.STARTED == MusicManager.getMusicManager().getState()){
            MusicManager.getMusicManager().pause();
        }
    }
}
