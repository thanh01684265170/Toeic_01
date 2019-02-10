package com.framgia.toeic.screen.base;

import android.media.MediaPlayer;

public class MediaPlayerInstance {
    public static MediaPlayer sMediaPlayer;

    public static MediaPlayer getInstance(){
        if (sMediaPlayer == null){
            sMediaPlayer = new MediaPlayer();
        }
        return sMediaPlayer;
    }
}
