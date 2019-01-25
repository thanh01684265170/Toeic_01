package com.framgia.toeic.screen.base;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.framgia.toeic.R;

import java.util.concurrent.TimeUnit;

import static com.framgia.toeic.screen.base.RatingResult.BAD;
import static com.framgia.toeic.screen.base.RatingResult.GOOD;
import static com.framgia.toeic.screen.base.RatingResult.NORMAL;
import static com.framgia.toeic.screen.base.RatingResult.VERY_BAD;
import static com.framgia.toeic.screen.base.RatingResult.VERY_GOOD;

public abstract class ResultTest extends BaseActivity implements View.OnClickListener {
    private static final String FORMAT_TIME = "%2d:%2d";
    public static final int TRANFER_SECOND_TO_MILISECOND = 1000;
    public static final int START_TIME = 0;
    protected Dialog mDialogResult;
    protected TextView mTextViewFalse;
    protected int mCountTime;
    protected Handler mHandler;
    protected TextView mTextViewTimeResult;

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
}
