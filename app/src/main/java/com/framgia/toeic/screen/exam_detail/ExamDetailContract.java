package com.framgia.toeic.screen.exam_detail;

import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;

import java.util.List;

public interface ExamDetailContract {
    interface View{
        void showDialogResult(int mark, int rating);
        void listenMedia();
        void pauseMedia();
    }

    interface Presenter{
        void checkAnswer(List<Exam> exams);
        void checkListening();
        void updateLesson(ExamLesson examLesson, int mark);
    }
}
