package com.framgia.toeic.screen.basic_test_detail;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;

import java.util.List;

public interface BasicTestDetailContract {
    interface View{
        void showBasicTest(List<BasicTest> basicTests);
        void showDialogResult(int mark, int rating);
        void listenMedia();
        void pauseMedia();
    }

    interface Presenter{
        void getBasicTest(BasicTestLesson basicTestLesson);
        void checkAnswer(List<BasicTest> basicTests);
        void checkListening();
    }
}
