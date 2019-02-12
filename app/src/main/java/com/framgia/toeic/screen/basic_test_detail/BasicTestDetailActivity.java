package com.framgia.toeic.screen.basic_test_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.local.BasicTestDatabaseHelper;
import com.framgia.toeic.data.source.local.BasicTestLocalDatasource;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.MediaPlayerInstance;
import com.framgia.toeic.screen.base.ResultTest;
import com.framgia.toeic.screen.base.ShowAnswerListener;
import com.framgia.toeic.screen.basic_test_detail.part_fragment.BasicTestDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class BasicTestDetailActivity extends ResultTest
        implements BasicTestDetailContract.View, ShowAnswerListener {
    private static final String EXTRA_BASIC_TEST_LESSON = "EXTRA_BASIC_TEST_LESSON";
    private static final String EXTENSION_MEDIA = ".mp3";
    private static final int TIMELINE = 15;
    private TextView mTextViewTime;
    private TextView mTextViewSubmit;
    private ImageView mImagePlayPause;
    private BasicTestLesson mLesson;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;
    private BasicTestDetailContract.Presenter mPresenter;

    public static Intent getIntent(Context context, BasicTestLesson basicTestLesson) {
        Intent intent = new Intent(context, BasicTestDetailActivity.class);
        intent.putExtra(EXTRA_BASIC_TEST_LESSON, basicTestLesson);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.material_accent_700));
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_basic_test_detail;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        mTextViewSubmit = findViewById(R.id.text_submit);
        mTextViewTime = findViewById(R.id.text_timer);
        mViewPager = findViewById(R.id.view_pager);
        mFragments = new ArrayList<>();
        mImagePlayPause = findViewById(R.id.image_play_pause);
        mPresenter = new BasicTestDetailPresenter(this, BasicTestRepository
                .getInstance(new BasicTestLocalDatasource(new BasicTestDatabaseHelper(
                        new DBHelper(this)))));
        mCountDownTimer = new CountDownTimer(
                TIMELINE * TRANFER_MINIUTE_TO_SECOND * TRANFER_SECOND_TO_MILISECOND,
                TRANFER_SECOND_TO_MILISECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextViewTime.setText(getStringDatefromlong(millisUntilFinished) + "");
            }

            @Override
            public void onFinish() {
                submitAnswer();
            }
        };
    }

    @Override
    protected void initData() {
        super.initData();
        mLesson = getIntent().getParcelableExtra(EXTRA_BASIC_TEST_LESSON);
        ViewPagerAdapter adapter =
                new ViewPagerAdapter(getSupportFragmentManager(), mLesson.getBasicTests(), mFragments);
        mViewPager.setAdapter(adapter);
        mTextViewSubmit.setOnClickListener(this);
        mImagePlayPause.setOnClickListener(this);
        playMedia(mLesson.getBasicTests().get(0).getIdImage(), EXTENSION_MEDIA);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPresenter.changeMediaFile(++i, mLesson.getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
     }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_submit:
                submitAnswer();
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                        notifyFragments();
                    }

                    @Override
                    public void onPageSelected(int i) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                break;
            case R.id.image_play_pause:
                mPresenter.checkListening();
        }
    }

    @Override
    public void listenMedia() {
        mImagePlayPause.setImageResource(R.drawable.ic_pause_button);
        MediaPlayerInstance.getInstance().start();
    }

    @Override
    public void pauseMedia() {
        mImagePlayPause.setImageResource(R.drawable.ic_play_button);
        MediaPlayerInstance.getInstance().pause();
    }

    @Override
    public void hideSeekBar() {
        MediaPlayerInstance.getInstance().stop();
        mSeekBar.setVisibility(View.GONE);
        mImagePlayPause.setVisibility(View.GONE);
    }

    @Override
    public void changeMedia(int id) {
        mSeekBar.setVisibility(View.VISIBLE);
        mSeekBar.setProgress(0);
        mImagePlayPause.setVisibility(View.VISIBLE);
        mImagePlayPause.setImageResource(R.drawable.ic_play_button);
        playMedia(id, EXTENSION_MEDIA);
    }

    @Override
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getBasicTests().size() - mark + "");
        mPresenter.updateLesson(mLesson, mark);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerInstance.getInstance().stop();
        mCountDownTimer.cancel();
    }

    @Override
    public void notifyFragments() {
        for (Fragment fragment: mFragments){
            DisplayAnswerListener displayAnswerListener = (DisplayAnswerListener) fragment;
            displayAnswerListener.showAnswer();
        }
    }

    public void submitAnswer() {
        mPresenter.checkAnswer(mLesson.getBasicTests());
        mCountDownTimer.cancel();
        MediaPlayerInstance.getInstance().stop();
        notifyFragments();
    }
}
