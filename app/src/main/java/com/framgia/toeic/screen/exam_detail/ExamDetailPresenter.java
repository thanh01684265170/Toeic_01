package com.framgia.toeic.screen.exam_detail;

import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.screen.base.MediaPlayerInstance;
import com.framgia.toeic.screen.base.RatingCaculator;
import com.framgia.toeic.screen.base.RatingResult;

import java.util.List;

public class ExamDetailPresenter extends RatingCaculator implements ExamDetailContract.Presenter {
    private ExamDetailContract.View mView;

    public ExamDetailPresenter(ExamDetailContract.View view) {
        mView = view;
    }

    public int calculateMark(List<Exam> exams) {
        int mark = 0;
        for (Exam exam : exams) {
            if (exam.isCheckAnswerA() && exam.getAnwserA().equals(exam.getResult())) {
                mark++;
            }

            if (exam.isCheckAnswerB() && exam.getAnwserB().equals(exam.getResult())) {
                mark++;
            }

            if (exam.isCheckAnswerC() && exam.getAnwserC().equals(exam.getResult())) {
                mark++;
            }

            if (exam.isCheckAnswerD() && exam.getAnwserD().equals(exam.getResult())) {
                mark++;
            }
        }
        return mark;
    }

    @Override
    public void checkAnswer(List<Exam> exams) {
        int mark = calculateMark(exams);
        @RatingResult int rating = rating(mark, exams.size());
        mView.showDialogResult(mark, rating);
    }

    @Override
    public void checkListening() {
        if (MediaPlayerInstance.getInstance().isPlaying()){
            mView.pauseMedia();
            return;
        }
        mView.listenMedia();
    }
}
