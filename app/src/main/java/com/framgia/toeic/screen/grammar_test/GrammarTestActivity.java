package com.framgia.toeic.screen.grammar_test;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.repository.GrammarLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.GrammarLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.GrammarLessonLocalDataSource;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.ResultTest;
import com.framgia.toeic.screen.base.ShowAnswerListener;
import com.framgia.toeic.screen.grammar_test.fragment_grammar.GrammarTestFragment;

import java.util.ArrayList;
import java.util.List;

public class GrammarTestActivity extends ResultTest implements ShowAnswerListener,
        GrammarTestContract.View, GrammarTestFragment.OnAnswerChangeListener, ViewPager.OnPageChangeListener {
    static final String EXTRA_LESSON_GRAMMAR = "EXTRA_LESSON_GRAMMAR";
    private static final int GRAMMAR_ID = 2;
    private ViewPager mViewPager;
    private TextView mTextViewCheck;
    private TextView mTextViewTime;
    private GrammarLesson mLesson;
    private List<Fragment> mFragments;
    private GrammarTestContract.Presenter mPresenter;

    public static Intent getIntent(Context context, GrammarLesson lesson) {
        Intent intent = new Intent(context, GrammarTestActivity.class);
        intent.putExtra(EXTRA_LESSON_GRAMMAR, lesson);
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
        return R.layout.activity_grammar_test;
    }

    @Override
    protected void initComponent() {
        mViewPager = findViewById(R.id.viewPager);
        mTextViewCheck = findViewById(R.id.text_submit);
        mTextViewCheck.setOnClickListener(this);
        mTextViewTime = findViewById(R.id.text_timer);
        mFragments = new ArrayList<>();
        mPresenter = new GrammarTestPresenter(this,
                GrammarLessonRepository.getInstance(new GrammarLessonLocalDataSource(
                        new GrammarLessonDatabaseHelper(new DBHelper(this)))));
        mHandler = new Handler();
        mCountTime = START_TIME;
    }

    @Override
    protected void initData() {
        mLesson = getIntent().getParcelableExtra(EXTRA_LESSON_GRAMMAR);
        GrammarViewPagerAdapter adapter = new GrammarViewPagerAdapter(
                getSupportFragmentManager(), mLesson.getGrammars(), mFragments);
        mViewPager.setAdapter(adapter);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCountTime++;
                mHandler.postDelayed(this, TRANFER_SECOND_TO_MILISECOND);
                mTextViewTime.setText(getStringDatefromlong(mCountTime*TRANFER_SECOND_TO_MILISECOND));
            }
        }, 0);
    }

    @Override
    public void notifyFragments() {
        for (Fragment fragment : mFragments) {
            DisplayAnswerListener displayAnswerListener = (DisplayAnswerListener) fragment;
            displayAnswerListener.showAnswer();
            displayAnswerListener.disableClick();
        }
    }

    @Override
    public void showError(Exception error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChanged(Grammar grammar) {
        int index = mLesson.getGrammars().indexOf(grammar);
        if (index != -1) {
            mLesson.getGrammars().get(index).setSelected(grammar.isSelected());
        }
    }

    @Override
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getGrammars().size() - mark + "");
        mTextViewTimeResult.setText(mTextViewTime.getText());
        mPresenter.updateLesson(mLesson, mark);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_submit:
                mPresenter.checkResult(GRAMMAR_ID, mLesson.getGrammars());
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
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
