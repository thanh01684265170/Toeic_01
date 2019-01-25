package com.framgia.toeic.screen.main;

import com.framgia.toeic.data.model.Mark;

import java.util.List;

public interface MainContract {
    interface View {
        void showValueProgressBar(List<Mark> marks);

        void setMaxSizeProgressBars(List<Integer> integers);

        void showError(Exception error);
    }

    interface Presenter {
        void getMarks();

        void getNumberQuestion();
    }
}
