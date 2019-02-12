package com.framgia.toeic.screen.exam;

import com.framgia.toeic.data.model.ExamLesson;

import java.util.List;

public interface ExamContract {
    interface View {
        void showExams(List<ExamLesson> examLessons);

        void showError(Exception e);
    }

    interface Presenter {
        void getExams();
        void updateMark(List<ExamLesson> lessons);
    }
}
