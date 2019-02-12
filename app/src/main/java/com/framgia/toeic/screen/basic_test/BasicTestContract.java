package com.framgia.toeic.screen.basic_test;

import com.framgia.toeic.data.model.BasicTestLesson;

import java.util.List;

public interface BasicTestContract {
    interface View{
        void showBasicTestLesson(List<BasicTestLesson> lessons);
    }

    interface Presenter{
        void getBasicTestLessons();
        void updateMark(List<BasicTestLesson> lessons);
    }
}
