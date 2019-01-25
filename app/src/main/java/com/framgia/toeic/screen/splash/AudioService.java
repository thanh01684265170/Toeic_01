package com.framgia.toeic.screen.splash;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

public class AudioService extends IntentService {
    private static List<String> data;
    private static final String FOLDER_AUDIO = "audio";
    private static final String NAME = "audio_service";

    public static Intent getIntentAudioService(Context context, List links) {
        data = links;
        Intent intent = new Intent(context, AudioService.class);
        return intent;
    }

    public AudioService() {
        super(NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Downloader downloadAudio = new Downloader(getBaseContext(), data);
        downloadAudio.download(new OnWriteData() {
            @Override
            public void writeFileError(Exception e) {

            }

            @Override
            public void writeFileSuccess(String link) {

            }
        }, FOLDER_AUDIO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
