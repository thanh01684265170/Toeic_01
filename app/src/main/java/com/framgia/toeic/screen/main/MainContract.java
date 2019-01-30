package com.framgia.toeic.screen.main;

public interface MainContract {
    interface View {
        void showError(Exception error);
    }

    interface Presenter {
        void getMarks();

        void getNumberQuestion();
    }
}
