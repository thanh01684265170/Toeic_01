package com.framgia.toeic.screen.exam_detail;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.repository.ExamLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.ExamLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.ExamLessonLocalDataSource;
import com.framgia.toeic.screen.base.MediaPlayerInstance;
import com.framgia.toeic.screen.base.ResultTest;

import java.io.File;
import java.io.IOException;

public class ExamDetailActivity extends ResultTest
        implements ExamDetailContract.View {
    private static final int TIMELINE = 120;
    private static final String EXTRA_EXAM_LESSON = "EXTRA_EXAM_LESSON";
    private static final String EXTENSION_MEDIA = ".m4a";
    private ExamLesson mLesson;
    private RecyclerView mRecyclerView;
    private TextView mTextViewSubmit;
    private TextView mTextViewTime;
    private ExamDetailContract.Presenter mPresenter;
    private ImageView mImagePlayPause;
    private ExamAdapter mAdapter;
    public static Intent getIntent(Context context, ExamLesson examLesson) {
        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra(EXTRA_EXAM_LESSON, examLesson);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_exam_detail;
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        mRecyclerView = findViewById(R.id.recycler_exam_detail);
        mTextViewSubmit = findViewById(R.id.text_submit);
        mTextViewTime = findViewById(R.id.text_timer_exam);
        mPresenter = new ExamDetailPresenter(this,
                ExamLessonRepository.getInstance(new ExamLessonLocalDataSource(
                        new ExamLessonDatabaseHelper(new DBHelper(this)))));
        mImagePlayPause = findViewById(R.id.image_play_pause);
        mCountDownTimer = new CountDownTimer(
                TIMELINE * TRANFER_MINIUTE_TO_SECOND * TRANFER_SECOND_TO_MILISECOND,
                TRANFER_SECOND_TO_MILISECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTextViewTime.setText(getStringDatefromlong(millisUntilFinished) + "");
            }

            @Override
            public void onFinish() {
                mPresenter.checkAnswer(mLesson.getExams());
            }
        };
    }

    @Override
    protected void initData() {
        super.initData();
        mLesson = getIntent().getExtras().getParcelable(EXTRA_EXAM_LESSON);
        mAdapter = new ExamAdapter(this, mLesson.getExams(), false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
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
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getExams().size() - mark + "");
        mPresenter.updateLesson(mLesson, mark);
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
    protected void onDestroy() {
        super.onDestroy();
        MediaPlayerInstance.getInstance().stop();
        mCountDownTimer.cancel();
    }

    public void submitAnswer(){
        mPresenter.checkAnswer(mLesson.getExams());
        mCountDownTimer.cancel();
        MediaPlayerInstance.getInstance().stop();
        mSeekBar.setEnabled(false);
        mAdapter.setChecked(true);
        mAdapter.notifyDataSetChanged();
    }
}
