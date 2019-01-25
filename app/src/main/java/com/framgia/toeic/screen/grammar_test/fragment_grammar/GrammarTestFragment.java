package com.framgia.toeic.screen.grammar_test.fragment_grammar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.QuestionFragment;

public class GrammarTestFragment extends QuestionFragment
        implements GrammarTestContract.View, DisplayAnswerListener, RadioGroup.OnCheckedChangeListener {
    static final String ARGUMENT_QUESTION = "ARGUMENT_QUESTION";
    static final String ARGUMENT_NUMBER_QUESTION = "ARGUMENT_NUMBER_QUESTION";
    private Grammar mGrammar;
    private GrammarTestContract.Presenter mPresenter;
    private OnAnswerChangeListener mCallback;

    public static Fragment newInstance(Grammar grammar, int numberQuestion) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_QUESTION, grammar);
        bundle.putInt(ARGUMENT_NUMBER_QUESTION, numberQuestion);
        Fragment fragment = new GrammarTestFragment();
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
        mGrammar = getArguments().getParcelable(ARGUMENT_QUESTION);
        mCurrentQuestionPosition = getArguments().getInt(ARGUMENT_NUMBER_QUESTION);
        mPresenter = new GrammarTestPresenter(this, mGrammar.getResult());
    }

    @Override
    public void showData() {
        int question = mCurrentQuestionPosition + 1;
        mTextViewNumberQuestion.setText(getResources().getString(R.string.title_question) + (question));
        mTextViewContentQuestion.setText(mGrammar.getQuestion());
        mRadioAnswerA.setText(mGrammar.getAnwserA());
        mRadioAnswerB.setText(mGrammar.getAnwserB());
        mRadioAnswerC.setText(mGrammar.getAnwserC());
        mRadioAnswerD.setText(mGrammar.getAnwserD());
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GrammarTestFragment.OnAnswerChangeListener) {
            mCallback = (GrammarTestFragment.OnAnswerChangeListener) context;
        }
    }

    @Override
    public void onRightAnswer() {
        mGrammar.setSelected(true);
        if (mCallback != null) {
            mCallback.onChanged(mGrammar);
        }
    }

    @Override
    public void onWrongAnswer() {
        mGrammar.setSelected(false);
        if (mCallback != null) {
            mCallback.onChanged(mGrammar);
        }
    }

    @Override
    public void onAnswerARight() {
        mRadioAnswerA.setBackgroundColor(Color.RED);
    }

    @Override
    public void onAnswerBRight() {
        mRadioAnswerB.setBackgroundColor(Color.RED);
    }

    @Override
    public void onAnswerCRight() {
        mRadioAnswerC.setBackgroundColor(Color.RED);
    }

    @Override
    public void onAnswerDRight() {
        mRadioAnswerD.setBackgroundColor(Color.RED);
    }

    @Override
    public void showAnswer() {
        mPresenter.validateAnswerA(mRadioAnswerA.getText().toString());
        mPresenter.validateAnswerB(mRadioAnswerB.getText().toString());
        mPresenter.validateAnwserC(mRadioAnswerC.getText().toString());
        mPresenter.validateAnwserD(mRadioAnswerD.getText().toString());
    }

    @Override
    public void disableClick() {
        mRadioAnswerA.setClickable(false);
        mRadioAnswerB.setClickable(false);
        mRadioAnswerC.setClickable(false);
        mRadioAnswerD.setClickable(false);
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
            case R.id.radio_d:
                result = mRadioAnswerD.getText().toString();
                break;
        }
        mPresenter.checkAnwser(result);
    }

    public interface OnAnswerChangeListener {
        void onChanged(Grammar grammar);
    }
}
