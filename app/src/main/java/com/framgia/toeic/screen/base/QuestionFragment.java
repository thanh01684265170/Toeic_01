package com.framgia.toeic.screen.base;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.framgia.toeic.R;

public abstract class QuestionFragment extends BaseFragment {
    protected TextView mTextViewNumberQuestion;
    protected TextView mTextViewContentQuestion;
    protected RadioButton mRadioAnswerA;
    protected RadioButton mRadioAnswerB;
    protected RadioButton mRadioAnswerC;
    protected RadioButton mRadioAnswerD;
    protected RadioGroup mRadioGroup;
    protected int mCurrentQuestionPosition;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_base;
    }

    @Override
    public void initComponent(View view) {
        mTextViewNumberQuestion = view.findViewById(R.id.text_number_question);
        mTextViewContentQuestion = view.findViewById(R.id.text_content_question);
        mRadioAnswerA = view.findViewById(R.id.radio_a);
        mRadioAnswerB = view.findViewById(R.id.radio_b);
        mRadioAnswerC = view.findViewById(R.id.radio_c);
        mRadioAnswerD = view.findViewById(R.id.radio_d);
        mRadioGroup = view.findViewById(R.id.radio_group);
    }
}
