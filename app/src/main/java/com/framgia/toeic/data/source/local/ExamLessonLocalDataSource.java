package com.framgia.toeic.data.source.local;

import com.framgia.toeic.data.ExamLessonDataSource;
import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class ExamLessonLocalDataSource implements ExamLessonDataSource.Local {
    private ExamLessonDatabaseHelper mDatabaseHelper;

    public ExamLessonLocalDataSource(ExamLessonDatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void getExamLessons(Callback<List<ExamLesson>> callback) {
        mDatabaseHelper.getExamLessons(callback);
    }

    @Override
    public void getExams(ExamLesson examLesson, Callback<List<Exam>> callback) {
        mDatabaseHelper.getExams(examLesson, callback);
    }
}
