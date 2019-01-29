package com.framgia.toeic.screen.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.repository.FileRepository;
import com.framgia.toeic.data.source.remote.FileRemoteDatasource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.main.MainActivity;

import java.util.List;

public class SplashActivity extends BaseActivity implements SplashContract.View, OnWriteData,
        FragmentAdapter.onNext {
    private static final int TIME = 3000;
    private static final String TEXT_BUTTON = "Tiếp";
    private SplashContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private Handler mHandler;
    private int mPageCurrent;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAdapter.getCount() == mPageCurrent) {
                mPageCurrent = 0;
            } else {
                mPageCurrent++;
            }
            mViewPager.setCurrentItem(mPageCurrent, true);
            mHandler.postDelayed(this, TIME);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getLinkAudioFiles();
        mPresenter.getLinkImageFiles();
        mHandler.postDelayed(mRunnable, TIME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_intro;
    }

    @Override
    protected void initComponent() {
        mViewPager = findViewById(R.id.viewpager);
        mAdapter = new FragmentAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mHandler = new Handler();
        mPresenter = new SplashPresenter(this, FileRepository.getInstance(
                new FileRepository(new FileRemoteDatasource())));

    }

    @Override
    protected void initData() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPageCurrent = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    public void position(String s, int i) {
        if (!s.equals(TEXT_BUTTON)) {
            startActivity(MainActivity.getMainIntent(SplashActivity.this));
        }
        mViewPager.setCurrentItem(i + 1, true);
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, TIME);
    }
}
