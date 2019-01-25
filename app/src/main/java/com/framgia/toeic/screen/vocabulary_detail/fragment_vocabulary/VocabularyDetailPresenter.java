package com.framgia.toeic.screen.vocabulary_detail.fragment_vocabulary;

public class VocabularyDetailPresenter implements VocabularyDetailContract.Presenter{
    private VocabularyDetailContract.View mView;
    private String mResult;

    public VocabularyDetailPresenter(VocabularyDetailContract.View view, String result) {
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
    public void displayAnwserA(String value) {
        if (value.equals(mResult)){
            mView.onAnswerARight();
        }
    }

    @Override
    public void displayAnwserB(String value) {
        if (value.equals(mResult)){
            mView.onAnswerBRight();
        }
    }

    @Override
    public void displayAnwserC(String value) {
        if (value.equals(mResult)){
            mView.onAnswerCRight();
        }
    }
}
