package com.framgia.toeic.screen.exam_detail;

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
