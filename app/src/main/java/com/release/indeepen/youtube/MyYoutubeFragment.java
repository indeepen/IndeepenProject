package com.release.indeepen.youtube;

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.release.indeepen.DefineContentType;

/**
 * Created by lyo on 2015-11-09.
 */
public class MyYoutubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    //private static MyYoutubeFragment instance;
    String sPath;

    public  MyYoutubeFragment () {
    }


    public void setPath(String path) {

        sPath = path;
        if (path.contains("https://youtu.be/")) {

            sPath = path.substring("https://youtu.be/".length());
        }
        initialize(DeveloperKey.DEVELOPER_KEY,this);
        //sPath = getArguments().getString(DefineContentType.YOUTUBE_PATH);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(sPath);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getActivity(), "Failured to Youtube!", Toast.LENGTH_LONG).show();
    }
}
