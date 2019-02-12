package com.framgia.toeic.screen.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.repository.FileRepository;
import com.framgia.toeic.data.source.remote.FileRemoteDatasource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.main.MainActivity;
import com.google.firebase.FirebaseApp;

import java.util.List;

public class SplashActivity extends BaseActivity implements SplashContract.View, OnWriteData,
        FragmentAdapter.onNext, View.OnClickListener {
    private static final int TIME = 3000;
    private static final int SIZE_DOT = 35;
    private static final int TOTAL_DOT = 3;
    private static final String CODE_DOT = "&#8226";
    private SplashContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private Handler mHandler;
    private int mPageCurrent;
    private LinearLayout mDotLayout;
    private TextView[] mDot;

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
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        addDots(0);
        FirebaseApp.initializeApp(this);
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
        mDotLayout = findViewById(R.id.linear_dot);
        mAdapter = new FragmentAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mHandler = new Handler();
        mPresenter = new SplashPresenter(this, FileRepository.getInstance(
                new FileRepository(new FileRemoteDatasource())));
    }

    public void addDots(int position) {
        mDotLayout.removeAllViews();
        mDot = new TextView[TOTAL_DOT];
        for (int i = 0; i < mDot.length; i++) {
            mDot[i] = new TextView(this);
            mDot[i].setText(Html.fromHtml(CODE_DOT));
            mDot[i].setTextSize(SIZE_DOT);
            mDot[i].setTextColor(getResources().getColor(R.color.tranperant_white));
            mDotLayout.addView(mDot[i]);
        }
        if (mDot.length > 0) {
            mDot[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void initData() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
                mPageCurrent = position;
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, TIME);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View view) {

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
        if (!s.equals(getResources().getString(R.string.button_intro_1))) {
            startActivity(MainActivity.getMainIntent(SplashActivity.this));
        }
        mViewPager.setCurrentItem(i + 1, true);
    }
}
