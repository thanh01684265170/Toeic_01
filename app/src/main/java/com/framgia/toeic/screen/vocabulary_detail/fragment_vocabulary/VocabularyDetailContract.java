package com.framgia.toeic.screen.vocabulary_detail.fragment_vocabulary;

public interface VocabularyDetailContract {
    interface View {
        void onRightAnswer();

        void onWrongAnswer();

        void onAnswerARight();

        void onAnswerBRight();

        void onAnswerCRight();
    }

    interface Presenter {
        void checkAnwser(String chosen);

        void displayAnwserA(String value);

        void displayAnwserB(String value);

        void displayAnwserC(String value);
    }
}
