package com.framgia.toeic.screen.base;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.toeic.R;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.framgia.toeic.screen.base.RatingResult.BAD;
import static com.framgia.toeic.screen.base.RatingResult.GOOD;
import static com.framgia.toeic.screen.base.RatingResult.NORMAL;
import static com.framgia.toeic.screen.base.RatingResult.VERY_BAD;
import static com.framgia.toeic.screen.base.RatingResult.VERY_GOOD;

public abstract class ResultTest extends BaseActivity implements View.OnClickListener {
    private static final String FORMAT_TIME = "%2d:%2d";
    public static final String FORLDER_MEDIA = "audio";
    public static final int TRANFER_MINIUTE_TO_SECOND = 60;
    public static final int TRANFER_SECOND_TO_MILISECOND = 1000;
    public static final int START_TIME = 0;
    protected Dialog mDialogResult;
    protected TextView mTextViewFalse;
    protected CountDownTimer mCountDownTimer;
    protected int mCountTime;
    protected Handler mHandler;
    protected SeekBar mSeekBar;
    protected TextView mTextViewTimeResult;

    @Override
    protected void initComponent() {
        mSeekBar = findViewById(R.id.seekBar);
        mHandler = new Handler(getMainLooper());
    }

    @Override
    protected void initData() {
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

    public void showDialogResult(int mark, int rating) {
        mDialogResult = new Dialog(this);
        mDialogResult.setContentView(R.layout.dialog_result);
        TextView textViewRating = mDialogResult.findViewById(R.id.text_rating);
        RatingBar ratingBar = mDialogResult.findViewById(R.id.ratingBar);
        TextView textViewTrue = mDialogResult.findViewById(R.id.text_number_question_true);
        mTextViewTimeResult = mDialogResult.findViewById(R.id.text_time);
        mTextViewFalse = mDialogResult.findViewById(R.id.text_number_question_false);
        Button buttonReview = mDialogResult.findViewById(R.id.button_review);
        Button buttonContinue = mDialogResult.findViewById(R.id.button_continue);
        ratingBar.setRating(rating);
        textViewTrue.setText(mark + "");
        buttonReview.setOnClickListener(this);
        buttonContinue.setOnClickListener(this);
        mDialogResult.setCanceledOnTouchOutside(false);
        mDialogResult.show();
        switch (rating) {
            case VERY_BAD:
                textViewRating.setText(getResources().getString(R.string.title_very_bad));
                break;
            case BAD:
                textViewRating.setText(getResources().getString(R.string.title_bad));
                break;
            case NORMAL:
                textViewRating.setText(getResources().getString(R.string.title_normal));
                break;
            case GOOD:
                textViewRating.setText(getResources().getString(R.string.title_good));
                break;
            case VERY_GOOD:
                textViewRating.setText(getResources().getString(R.string.title_very_good));
                break;
        }
    }

    protected String getStringDatefromlong(long countTime) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(countTime);
        long second = TimeUnit.MILLISECONDS.toSeconds(countTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(countTime));
        return (minutes < 10 ? "0" : "") + minutes +":"+(second < 10 ? "0" : "") + second;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_review:
                mDialogResult.dismiss();
                break;
            case R.id.button_continue:
                finish();
                break;
        }
    }

    public void playMedia(int id, String extension) {
        ContextWrapper cw = new ContextWrapper(this);
        File directory = cw.getDir( FORLDER_MEDIA +
                "", Context.MODE_PRIVATE);
        File mypath = new File(directory, id + extension);
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
}
