package com.framgia.toeic.screen.basic_test;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class BasicTestPresenter implements BasicTestContract.Presenter {
    private static final int ID_BASIC_TEST = 3;
    private BasicTestContract.View mView;
    private BasicTestRepository mRepository;
    private MarkRepository mMarkRepository;

    public BasicTestPresenter(BasicTestContract.View view, BasicTestRepository repository, MarkRepository markRepository) {
        mView = view;
        mRepository = repository;
        mMarkRepository = markRepository;
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

    @Override
    public void updateMark(List<BasicTestLesson> lessons) {
        int totalMark = 0;
        for (BasicTestLesson lesson: lessons){
            totalMark += lesson.getRating();
        }
        mMarkRepository.updateMark(ID_BASIC_TEST, totalMark);
    }
}
