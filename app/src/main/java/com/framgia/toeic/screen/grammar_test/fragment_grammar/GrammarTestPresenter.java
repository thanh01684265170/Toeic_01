package com.framgia.toeic.screen.grammar_test.fragment_grammar;

public class GrammarTestPresenter implements GrammarTestContract.Presenter{
    private GrammarTestContract.View mView;
    private String mResult;

    public GrammarTestPresenter(GrammarTestContract.View view, String result) {
        mView = view;
        mResult = result;
    }

    @Override
    public void checkAnwser(String chosen) {
        if (chosen.equals(mResult)){
            mView.onRightAnswer();
        } else {
            mView.onWrongAnswer();
        }
    }

    @Override
    public void validateAnswerA(String value) {
        if (value.equals(mResult)){
            mView.onAnswerARight();
        }
    }

    @Override
    public void validateAnswerB(String value) {
        if (value.equals(mResult)){
            mView.onAnswerBRight();
        }
    }

    @Override
    public void validateAnwserC(String value) {
        if (value.equals(mResult)){
            mView.onAnswerCRight();
        }
    }

    @Override
    public void validateAnwserD(String value) {
        if (value.equals(mResult)){
            mView.onAnswerDRight();
        }
    }
}
