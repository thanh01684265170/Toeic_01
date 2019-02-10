package com.framgia.toeic.screen.basic_test;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class BasicTestPresenter implements BasicTestContract.Presenter {
    private BasicTestContract.View mView;
    private BasicTestRepository mRepository;

    public BasicTestPresenter(BasicTestContract.View view, BasicTestRepository examLessonRepository) {
        mView = view;
        mRepository = examLessonRepository;
    }

    public void setBasicTest(final BasicTestLesson lesson) {
        mRepository.getBasicTests(lesson, new Callback<List<BasicTest>>() {
            @Override
            public void onGetDataSuccess(List<BasicTest> basicTests) {
                lesson.setBasicTests(basicTests);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }

    @Override
    public void getBasicTestLessons() {
        mRepository.getBasicTestLessons(new Callback<List<BasicTestLesson>>() {
            @Override
            public void onGetDataSuccess(List<BasicTestLesson> basicTestLessons) {
                for (BasicTestLesson lesson : basicTestLessons) {
                    setBasicTest(lesson);
                }
                mView.showBasicTestLesson(basicTestLessons);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }
}
