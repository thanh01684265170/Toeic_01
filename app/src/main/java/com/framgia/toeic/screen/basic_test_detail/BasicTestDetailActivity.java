package com.framgia.toeic.screen.basic_test_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.local.BasicTestDatabaseHelper;
import com.framgia.toeic.data.source.local.BasicTestLocalDatasource;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.screen.base.MediaPlayerInstance;
import com.framgia.toeic.screen.base.ResultTest;

import java.util.List;

public class BasicTestDetailActivity extends ResultTest implements BasicTestDetailContract.View {
    private static final String EXTRA_BASIC_TEST_LESSON = "EXTRA_BASIC_TEST_LESSON";
    private static final String EXTENSION_MEDIA = ".mp3";
    private static final int TIMELINE = 15;
    private RecyclerView mRecyclerBasicTest;
    private TextView mTextViewTime;
    private TextView mTextViewSubmit;
    private ImageView mImagePlayPause;
    private BasicTestLesson mLesson;
    private BasicTestDetailAdapter mAdapter;
    private BasicTestDetailContract.Presenter mPresenter;

    public static Intent getIntent(Context context, BasicTestLesson basicTestLesson) {
        Intent intent = new Intent(context, BasicTestDetailActivity.class);
        intent.putExtra(EXTRA_BASIC_TEST_LESSON, basicTestLesson);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_basic_test_detail;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        mRecyclerBasicTest = findViewById(R.id.recycler_basic_test);
        mTextViewSubmit = findViewById(R.id.text_submit);
        mTextViewTime = findViewById(R.id.text_timer);
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
        mPresenter.getBasicTest(mLesson);
        mTextViewSubmit.setOnClickListener(this);
        mImagePlayPause.setOnClickListener(this);
        playMedia(mLesson.getId(), EXTENSION_MEDIA);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_submit:
                submitAnswer();
                break;
            case R.id.image_play_pause:
                mPresenter.checkListening();
        }
    }

    @Override
    public void showBasicTest(List<BasicTest> basicTests) {
        mLesson.setBasicTests(basicTests);
        mAdapter = new BasicTestDetailAdapter(this, basicTests, false);
        mRecyclerBasicTest.setAdapter(mAdapter);
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
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getBasicTests().size() - mark + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerInstance.getInstance().stop();
        mCountDownTimer.cancel();
    }

    public void submitAnswer() {
        mPresenter.checkAnswer(mLesson.getBasicTests());
        mCountDownTimer.cancel();
        MediaPlayerInstance.getInstance().stop();
        mAdapter.setChecked(true);
        mAdapter.notifyDataSetChanged();
    }
}
