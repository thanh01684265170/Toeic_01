package com.framgia.toeic.screen.exam;

import android.util.Log;

import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.repository.ExamLessonRepository;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class ExamPresenter implements ExamContract.Presenter {
    private static final int ID_EXAM = 4;
    private ExamContract.View mView;
    private ExamLessonRepository mRepository;
    private MarkRepository mMarkRepository;

    public ExamPresenter(ExamContract.View view, ExamLessonRepository repository, MarkRepository markRepository) {
        mView = view;
        mRepository = repository;
        mMarkRepository = markRepository;
    }

    public void setExam(final ExamLesson examLesson) {
        mRepository.getExams(examLesson, new Callback<List<Exam>>() {
            @Override
            public void onGetDataSuccess(List<Exam> exams) {
                examLesson.setExams(exams);
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }

    @Override
    public void getExams() {
        mRepository.getExamLessons(new Callback<List<ExamLesson>>() {
            @Override
            public void onGetDataSuccess(List<ExamLesson> examLessons) {
                mView.showExams(examLessons);
                for (ExamLesson examLesson : examLessons) {
                    setExam(examLesson);
                }
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }


    @Override
    public void updateMark(List<ExamLesson> lessons) {
        int totalMark = 0;
        for (ExamLesson lesson: lessons){
            totalMark += lesson.getRating();
        }
        mMarkRepository.updateMark(ID_EXAM, totalMark);
    }
}
