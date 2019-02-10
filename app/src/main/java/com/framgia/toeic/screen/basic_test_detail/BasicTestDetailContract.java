package com.framgia.toeic.screen.basic_test_detail;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;

import java.util.List;

public interface BasicTestDetailContract {
    interface View {
        void showDialogResult(int mark, int rating);

        void listenMedia();

        void pauseMedia();
    }

    interface Presenter {
        void checkAnswer(List<BasicTest> basicTests);

        void checkListening();

        void updateLesson(BasicTestLesson lesson, int mark);

    }
}
