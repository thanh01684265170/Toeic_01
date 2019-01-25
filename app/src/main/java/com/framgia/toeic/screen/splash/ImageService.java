package com.framgia.toeic.screen.splash;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

public class ImageService extends IntentService {
    private static List<String> data;
    private static final String FOLDER_IMAGE = "image";
    private static final String NAME = "image_service";

    public static Intent getIntentImageService(Context context, List links) {
        ImageService.data = links;
        Intent intent = new Intent(context, ImageService.class);
        return intent;
    }

    public ImageService() {
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
        Downloader downloadImage = new Downloader(getBaseContext(),data);
        downloadImage.download(new OnWriteData() {
            @Override
            public void writeFileError(Exception e) {

            }

            @Override
            public void writeFileSuccess(String link) {

            }
        },FOLDER_IMAGE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
