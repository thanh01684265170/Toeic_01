package com.framgia.toeic.screen.vocabulary_detail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.MarkDatabaseHelper;
import com.framgia.toeic.data.source.local.MarkLocalDataSource;
import com.framgia.toeic.screen.base.DepthPageTransformer;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.ResultTest;
import com.framgia.toeic.screen.base.ShowAnswerListener;
import com.framgia.toeic.screen.vocabulary_detail.fragment_vocabulary.VocabularyDetailFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VocabularyDetailActivity extends ResultTest
        implements VocabularyDetailContract.View, VocabularyDetailFragment.OnAnswerChangeListener,
        ShowAnswerListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String EXTRA_LIST_QUESTION = "EXTRA_LIST_QUESTION";
    private static final int ID_VOCABULARY = 1;
    private ViewPager mViewPager;
    private TextView mTextViewCheck;
    private TextView mTextViewTime;
    private ArrayList<Vocabulary> mVocabularies;
    private List<Fragment> mVocabularyFragments;
    private VocabularyDetailContract.Presenter mPresenter;

    public static Intent getIntent(Context context, List<Vocabulary> vocabularies) {
        Intent intent = new Intent(context, VocabularyDetailActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_LIST_QUESTION, (ArrayList<? extends Parcelable>) vocabularies);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_vocabulary_detail;
    }

    @Override
    protected void initComponent() {
        mViewPager = findViewById(R.id.viewPager);
        mTextViewCheck = findViewById(R.id.text_submit);
        mTextViewCheck.setOnClickListener(this);
        mTextViewTime = findViewById(R.id.text_timer);
        mVocabularyFragments = new ArrayList();
        mPresenter = new VocabularyDetailPresenter(this,
                MarkRepository.getInstance(new MarkLocalDataSource(
                        new MarkDatabaseHelper(new DBHelper(this)))));
        mHandler = new Handler();
        mCountTime = START_TIME;
    }

    @Override
    protected void initData() {
        mVocabularies = getIntent().getParcelableArrayListExtra(EXTRA_LIST_QUESTION);
        VocabularyViewPagerAdapter adapter = new VocabularyViewPagerAdapter(
                getSupportFragmentManager(), mVocabularies, mVocabularyFragments);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(adapter);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, TRANFER_SECOND_TO_MILISECOND);
                mCountTime++;
                mTextViewTime.setText(getStringDatefromlong(mCountTime));
            }
        }, 0);
    }

    @Override
    public void onChanged(Vocabulary vocabulary) {
        int index = mVocabularies.indexOf(vocabulary);
        if (index != -1) {
            mVocabularies.get(index).setSelected(vocabulary.isSelected());
        }
    }

    @Override
    public void notifyFragments() {
        for (Fragment fragment : mVocabularyFragments) {
            DisplayAnswerListener displayAnswerListener = (DisplayAnswerListener) fragment;
            displayAnswerListener.showAnswer();
            displayAnswerListener.disableClick();
        }
    }

    @Override
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mark+"");
        mTextViewTimeResult.setText(mTextViewTime.getText());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_submit:
                mPresenter.checkResult(ID_VOCABULARY,  mVocabularies);
                mHandler.removeCallbacksAndMessages(null);
                notifyFragments();
                mViewPager.addOnPageChangeListener(this);
                break;
            case R.id.button_review:
                mDialogResult.dismiss();
                break;
            case R.id.button_continue:
                finish();
                break;
        }
    }

    @Override
    public void showError(Exception error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

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
}
