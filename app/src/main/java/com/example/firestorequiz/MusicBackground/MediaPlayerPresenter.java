package com.example.firestorequiz.MusicBackground;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.firestorequiz.R;

public class MediaPlayerPresenter {

    private static MediaPlayerPresenter mPresenter;

    private MediaPlayer mPlayer;

    private MediaPlayerPresenter(Context context) {
        mPlayer = MediaPlayer.create(context, R.raw.jingle);
        mPlayer.setLooping(true);
    }


    public static MediaPlayerPresenter getInstance(Context context) {
        if (mPresenter == null) {
            mPresenter = new MediaPlayerPresenter(context);
        }
        return mPresenter;
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    public boolean isPlaying() {

        return mPlayer.isPlaying();
    }

    public void playMusic() {
        mPlayer.start();

    }
}
