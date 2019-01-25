package com.framgia.toeic.screen.basic_test;

import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class BasicTestPresenter implements BasicTestContract.Presenter{
    private BasicTestContract.View mView;
    private BasicTestRepository mExamLessonRepository;

    public BasicTestPresenter(BasicTestContract.View view, BasicTestRepository examLessonRepository) {
        mView = view;
        mExamLessonRepository = examLessonRepository;
    }

    @Override
    public void getBasicTestLessons() {
        mExamLessonRepository.getBasicTestLessons(new Callback<List<BasicTestLesson>>() {
            @Override
            public void onGetDataSuccess(List<BasicTestLesson> basicTestLessons) {
                mView.showBasicTestLesson(basicTestLessons);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }
}
