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
import com.framgia.toeic.screen.base.ResultTest;

import java.io.File;
import java.io.IOException;

public class ExamDetailActivity extends ResultTest
        implements ExamDetailContract.View {
    private static final String EXTRA_EXAM_LESSON = "EXTRA_EXAM_LESSON";
    private static final int TRANFER_MINIUTE_TO_SECOND = 60;
    private static final int TIMELINE = 120;
    private static final String EXTENSION_MEDIA = ".m4a";
    private static final String FORLDER_MEDIA = "audio";
    private ExamLesson mLesson;
    private RecyclerView mRecyclerView;
    private TextView mTextViewSubmit;
    private TextView mTextViewTime;
    private ExamDetailContract.Presenter mPresenter;
    private SeekBar mSeekBar;
    private ImageView mImagePlayPause;
    private CountDownTimer mCountDownTimer;
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
        mRecyclerView = findViewById(R.id.recycler_exam_detail);
        mTextViewSubmit = findViewById(R.id.text_submit);
        mTextViewTime = findViewById(R.id.text_timer_exam);
        mPresenter = new ExamDetailPresenter(this);
        mSeekBar = findViewById(R.id.seek_bar_listening);
        mImagePlayPause = findViewById(R.id.image_play_pause);
        mHandler = new Handler(getMainLooper());
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
        mLesson = getIntent().getExtras().getParcelable(EXTRA_EXAM_LESSON);
        mAdapter = new ExamAdapter(this, mLesson.getExams(), false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mTextViewSubmit.setOnClickListener(this);
        mImagePlayPause.setOnClickListener(this);
        playMedia();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress(MediaPlayerInstance.getInstance().getCurrentPosition());
                mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        MediaPlayerInstance.getInstance().pause();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        MediaPlayerInstance.getInstance().seekTo(seekBar.getProgress());
                        MediaPlayerInstance.getInstance().start();
                    }
                });
                mHandler.postDelayed(this, TRANFER_SECOND_TO_MILISECOND);
            }
        }, 0);
        mCountDownTimer.start();
    }

    public void playMedia() {
        ContextWrapper cw = new ContextWrapper(this);
        File directory = cw.getDir( FORLDER_MEDIA +
                "", Context.MODE_PRIVATE);
        File mypath = new File(directory, mLesson.getId() + EXTENSION_MEDIA);
        try {
            MediaPlayerInstance.getInstance().setAudioStreamType(AudioManager.STREAM_MUSIC);
            MediaPlayerInstance.getInstance().reset();
            MediaPlayerInstance.getInstance().setDataSource(mypath.getPath());
            MediaPlayerInstance.getInstance().prepare();
            MediaPlayerInstance.getInstance().start();
            mSeekBar.setMax(MediaPlayerInstance.getInstance().getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_submit:
                mPresenter.checkAnswer(mLesson.getExams());
                mCountDownTimer.cancel();
                MediaPlayerInstance.getInstance().stop();
                mSeekBar.setEnabled(false);
                mAdapter.setChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.image_play_pause:
                mPresenter.checkListening();
        }
    }

    @Override
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getExams().size() - mark + "");
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
}
