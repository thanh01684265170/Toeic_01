package com.framgia.toeic.screen.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.repository.FileRepository;
import com.framgia.toeic.data.source.remote.FileRemoteDatasource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.main.MainActivity;
import com.google.firebase.FirebaseApp;

import java.util.List;

public class SplashActivity extends BaseActivity implements SplashContract.View, OnWriteData {
    private static final int TIME = 2000;
    private ImageView mImageView;
    private SplashContract.Presenter mPresenter;
    private Animation mAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_fadein);
        mAnimation.setDuration(TIME);
        mImageView.startAnimation(mAnimation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getMainIntent(SplashActivity.this));
            }
        }, TIME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getLinkAudioFiles();
        mPresenter.getLinkImageFiles();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void initComponent() {
        mImageView = findViewById(R.id.image_splash_screen);
        mPresenter = new SplashPresenter(this, FileRepository.getInstance(
                new FileRepository(new FileRemoteDatasource())));
    }

    @Override
    protected void initData() {
    }

    @Override
    public void downloadAudioFile(List<String> links) {
        startService(AudioService.getIntentAudioService(getApplicationContext(), links));
    }

    @Override
    public void downloadImageFile(List<String> links) {
        startService(ImageService.getIntentImageService(getApplicationContext(), links));
    }

    @Override
    public void writeFileError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void writeFileSuccess(String success) {

    }
}
