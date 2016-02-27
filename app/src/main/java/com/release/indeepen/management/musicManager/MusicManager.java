package com.release.indeepen.management.musicManager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.SeekBar;

import com.release.indeepen.SharedApplication;

import java.io.IOException;

/**
 * Created by lyo on 2015-11-06.
 */
public class MusicManager {
    private static MusicManager musicManager;
    private AudioManager mAM;
    private static MediaPlayer mPlayer;
    private SeekBar seekBar;
    boolean isSeeking = false;
    int max;
    public enum PlayState {
        IDLE,
        INITIALIZED,
        PREPARED,
        STARTED,
        PAUSED,
        STOPPED,
        ERROR,
        RELEASED
    }

    public static PlayState mState = PlayState.IDLE;

    private MusicManager() {

    }

    public static MusicManager getMusicManager() {
        if (null == musicManager) {
            musicManager = new MusicManager();

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mState = PlayState.IDLE;
            mState = PlayState.INITIALIZED;
            mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mState = PlayState.ERROR;
                    return false;
                }
            });
        }
        return musicManager;
    }


    public MediaPlayer getMusicPlayer() {
        return mPlayer;
    }

    public void init(SeekBar vSeek) {
        seekBar = vSeek;

        max = getDuration();
        vSeek.setMax(max);
        vSeek.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //if (fromUser) {
                this.progress = progress;
                // }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                progress = -1;
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progress != -1) {
                    if (mState == PlayState.STARTED) {
                        mPlayer.seekTo(progress);
                    }
                }
                isSeeking = false;
            }
        });
    }

    public void seekbarReset(SeekBar vSeek) {
        vSeek.setMax(getDuration());
        mPlayer.seekTo(vSeek.getProgress());
    }


    public void reset() {
        //if(mState == PlayState.STARTED ||mState == PlayState.PAUSED||mState == PlayState.PREPARED){
        mPlayer.reset();
        mState = PlayState.IDLE;
        // }
    }

    public AudioManager getAudioManager() {
        if (null == mAM) {
            mAM = (AudioManager) SharedApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        }
        return mAM;
    }

    public void setMyURI(Uri uri) {

        try {
            reset();
            mPlayer.setDataSource(SharedApplication.getContext(), uri);
            mState = PlayState.INITIALIZED;
            mPlayer.prepare();
            mState = PlayState.PREPARED;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMyURL(String url) {

        try {
            if(TextUtils.isEmpty(url)) return;
            reset();
            mPlayer.setDataSource(url);
            mState = PlayState.INITIALIZED;
            mPlayer.prepare();
            mState = PlayState.PREPARED;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int CHECK_INTERVAL = 100;
    Handler mHandler = new Handler(Looper.getMainLooper());
    Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mState == PlayState.STARTED) {
                if (!isSeeking) {
                    int position = mPlayer.getCurrentPosition();
                    seekBar.setProgress(position);

                }
                if(seekBar.getProgress() == max){
                    seekBar.setProgress(0);
                    musicManager.pause();
                }else {
                    mHandler.postDelayed(this, CHECK_INTERVAL);
                }
            }
        }
    };

    public void play() {
        if (mState == PlayState.INITIALIZED || mState == PlayState.STOPPED) {
            try {
                mPlayer.prepare();
                mState = PlayState.PREPARED;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mState == PlayState.PREPARED || mState == PlayState.PAUSED) {
            mPlayer.seekTo(seekBar.getProgress());
            mPlayer.start();
            mState = PlayState.STARTED;
            mHandler.post(progressRunnable);
        }
    }

    public void pause() {
        if (mState == PlayState.STARTED) {
            mPlayer.pause();
            mState = PlayState.PAUSED;
        }
    }

    public void stop() {
        if (null == seekBar) return;
        if (mState == PlayState.STARTED || mState == PlayState.PREPARED || mState == PlayState.PAUSED) {
            mPlayer.stop();
            mState = PlayState.STOPPED;
            seekBar.setProgress(0);
            mPlayer.release();
            mState = PlayState.RELEASED;
        }
    }

    public PlayState getState() {
        return mState;
    }

    public int getDuration() {
        return mPlayer.getDuration();
    }


}
