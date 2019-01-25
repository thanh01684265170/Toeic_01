package com.framgia.toeic.screen.grammar_test.fragment_grammar;

public interface GrammarTestContract {
    interface View {
        void onRightAnswer();

        void onWrongAnswer();

        void onAnswerARight();

        void onAnswerBRight();

        void onAnswerCRight();

        void onAnswerDRight();
    }

    interface Presenter {
        void checkAnwser(String chosen);

        void validateAnswerA(String value);

        void validateAnswerB(String value);

        void validateAnwserC(String value);

        void validateAnwserD(String value);
    }
}
