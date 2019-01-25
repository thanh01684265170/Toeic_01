package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.ExamLessonDataSource;
import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class ExamLessonRepository implements ExamLessonDataSource.Local {
    public static ExamLessonRepository sExamLessonRepository;
    private ExamLessonDataSource.Local mLocal;

    public ExamLessonRepository(ExamLessonDataSource.Local local) {
        mLocal = local;
    }

    public static ExamLessonRepository getInstance(ExamLessonDataSource.Local local) {
        if (sExamLessonRepository == null) {
            sExamLessonRepository = new ExamLessonRepository(local);
        }
        return sExamLessonRepository;
    }

    @Override
    public void getExamLessons(Callback<List<ExamLesson>> callback) {
        mLocal.getExamLessons(callback);
    }

    @Override
    public void getExams(ExamLesson examLesson, Callback<List<Exam>> callback) {
        mLocal.getExams(examLesson, callback);
    }
}
