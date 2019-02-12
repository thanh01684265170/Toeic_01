package com.framgia.toeic.screen.basic_test_detail;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.Callback;
import com.framgia.toeic.screen.base.MediaPlayerInstance;
import com.framgia.toeic.screen.base.RatingCaculator;
import com.framgia.toeic.screen.base.RatingResult;

import java.util.List;

public class BasicTestDetailPresenter extends RatingCaculator implements BasicTestDetailContract.Presenter {
    private BasicTestDetailContract.View mView;
    private BasicTestRepository mRepository;

    public BasicTestDetailPresenter(BasicTestDetailContract.View view, BasicTestRepository repository) {
        mView = view;
        mRepository = repository;
    }

    public int calculateMark(List<BasicTest> basicTests) {
        int mark = 0;
        for (BasicTest basicTest : basicTests) {
            if (basicTest.isCheckAnswerA() && basicTest.getAnwserA().equals(basicTest.getResult())) {
                mark++;
            }

            if (basicTest.isCheckAnswerB() && basicTest.getAnwserB().equals(basicTest.getResult())) {
                mark++;
            }

            if (basicTest.isCheckAnswerC() && basicTest.getAnwserC().equals(basicTest.getResult())) {
                mark++;
            }

            if (basicTest.isCheckAnswerD() && basicTest.getAnwserD().equals(basicTest.getResult())) {
                mark++;
            }
        }
        return mark;
    }

    @Override
    public void checkAnswer(List<BasicTest> basicTests) {
        int mark = calculateMark(basicTests);
        @RatingResult int rating = rating(mark, basicTests.size());
        mView.showDialogResult(mark, rating);
    }

    @Override
    public void checkListening() {
        if (MediaPlayerInstance.getInstance().isPlaying()) {
            mView.pauseMedia();
            return;
        }
        mView.listenMedia();
    }

    @Override
    public void updateLesson(BasicTestLesson lesson, int mark) {
        if (mark > lesson.getRating()){
            mRepository.updateBasicTestLesson(lesson, mark, new Callback<BasicTestLesson>() {
                @Override
                public void onGetDataSuccess(BasicTestLesson examLesson) {

                }

                @Override
                public void onGetDataFail(Exception error) {

                }
            });
            lesson.setRating(mark);
        }
    }

    @Override
    public void changeMediaFile(int part, int idLesson) {
        if (part <= 4){
            int id = (idLesson - 1)*4 + part;
            mView.changeMedia(id);
            return;
        }
        mView.hideSeekBar();
    }
}
