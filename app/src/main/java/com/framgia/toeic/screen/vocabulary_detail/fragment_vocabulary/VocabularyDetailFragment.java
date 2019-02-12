package com.framgia.toeic.screen.vocabulary_detail.fragment_vocabulary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.QuestionFragment;

public class VocabularyDetailFragment extends QuestionFragment
        implements RadioGroup.OnCheckedChangeListener, VocabularyDetailContract.View,
        DisplayAnswerListener, Runnable {
    private static final String ARGUMENT_QUESTION = "ARGUMENT_QUESTION";
    private static final String ARGUMENT_NUMBER_QUESTION = "ARGUMENT_NUMBER_QUESTION";
    private static final String ARGUMENT_TOTAL_NUMBER_QUESTION = "ARGUMENT_TOTAL_NUMBER_QUESTION";
    private VocabularyDetailContract.Presenter mPresenter;
    private Vocabulary mVocabulary;
    private int mCurrentQuestionPosition;
    private int mTotalNumberQuestion;
    private OnAnswerChangeListener mCallback;

    public static Fragment newInstance(Vocabulary vocabulary, int numberQuestion, int totalNumberQuestion) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_QUESTION, vocabulary);
        bundle.putInt(ARGUMENT_NUMBER_QUESTION, numberQuestion);
        bundle.putInt(ARGUMENT_TOTAL_NUMBER_QUESTION, totalNumberQuestion);
        Fragment fragment = new VocabularyDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initComponent(View view) {
        super.initComponent(view);
    }

    @Override
    public void initData() {
        mVocabulary = getArguments().getParcelable(ARGUMENT_QUESTION);
        mCurrentQuestionPosition = getArguments().getInt(ARGUMENT_NUMBER_QUESTION);
        mTotalNumberQuestion = getArguments().getInt(ARGUMENT_TOTAL_NUMBER_QUESTION);
        mPresenter = new VocabularyDetailPresenter(this, mVocabulary.getResult());
    }

    @Override
    public void showData() {
        int question = mCurrentQuestionPosition + 1;
        mTextViewNumberQuestion.setText(getResources().getString(R.string.title_question) + (question)+"/"+ mTotalNumberQuestion);
        StringBuilder builder = new StringBuilder();
        builder.append(mVocabulary.getQuestion()).append(getString(R.string.title_vocabulary_question));
        mTextViewContentQuestion.setText(builder.toString());
        mRadioAnswerA.setText(mVocabulary.getAnwserA());
        mRadioAnswerB.setText(mVocabulary.getAnwserB());
        mRadioAnswerC.setText(mVocabulary.getAnwserC());
        mRadioAnswerD.setVisibility(View.GONE);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAnswerChangeListener) {
            mCallback = (OnAnswerChangeListener) context;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String result = null;
        switch (checkedId) {
            case R.id.radio_a:
                result = mRadioAnswerA.getText().toString();
                break;
            case R.id.radio_b:
                result = mRadioAnswerB.getText().toString();
                break;
            case R.id.radio_c:
                result = mRadioAnswerC.getText().toString();
                break;
        }
        mPresenter.checkAnwser(result);
    }

    @Override
    public void onRightAnswer() {
        mVocabulary.setSelected(true);
        if (mCallback != null) {
            mCallback.onChanged(mVocabulary);
        }
    }

    @Override
    public void onWrongAnswer() {
        mVocabulary.setSelected(false);
        if (mCallback != null) {
            mCallback.onChanged(mVocabulary);
        }
    }

    @Override
    public void onAnswerARight() {
        mRadioAnswerA.setTextColor(getResources().getColor(R.color.material_red_dark_600));
    }

    @Override
    public void onAnswerBRight() {
        mRadioAnswerB.setTextColor(getResources().getColor(R.color.material_red_dark_600));
    }

    @Override
    public void onAnswerCRight() {
        mRadioAnswerC.setTextColor(getResources().getColor(R.color.material_red_dark_600));
    }

    @Override
    public void showAnswer() {
       if (!mVocabulary.isSelected()){
           mPresenter.displayAnwserA(mRadioAnswerA.getText().toString());
           mPresenter.displayAnwserB(mRadioAnswerB.getText().toString());
           mPresenter.displayAnwserC(mRadioAnswerC.getText().toString());
       }
    }

    @Override
    public void disableClick() {
        mRadioAnswerA.setClickable(false);
        mRadioAnswerB.setClickable(false);
        mRadioAnswerC.setClickable(false);
    }

    @Override
    public void run() {
    }

    public interface OnAnswerChangeListener {
        void onChanged(Vocabulary vocabulary);
    }
}
