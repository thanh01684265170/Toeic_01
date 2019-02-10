package com.framgia.toeic.data;

import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public interface ExamLessonDataSource {
    interface Local {
        void getExamLessons(Callback<List<ExamLesson>> callback);

        void getExams(ExamLesson examLesson, Callback<List<Exam>> callback);

        void updateExamLesson(ExamLesson examLesson, int mark, Callback<ExamLesson> callback);
    }

    interface Remote {
    }
}
