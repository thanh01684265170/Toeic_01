package com.framgia.toeic.screen.vocabulary_detail;


import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;
import com.framgia.toeic.screen.base.RatingCaculator;
import com.framgia.toeic.screen.base.RatingResult;

import java.util.List;

public class VocabularyDetailPresenter extends RatingCaculator implements VocabularyDetailContract.Presenter {


    private VocabularyDetailContract.View mView;
    private MarkRepository mRepository;

    public VocabularyDetailPresenter(VocabularyDetailContract.View view, MarkRepository repository) {
        mView = view;
        mRepository = repository;
    }

    /**
     * Count the score from vocabularies by selected right answer
     * @param vocabularies
     * @return
     */
    private int getScore(List<Vocabulary> vocabularies){
        int score = 0;
        for (Vocabulary vocabulary : vocabularies) {
            if (vocabulary.isSelected()) {
                score++;
            }
        }
        return score;
    }

    @Override
    public void checkResult(final int id, List<Vocabulary> vocabularies) {
        final int score = getScore(vocabularies);
        @RatingResult final int rating = rating(score, vocabularies.size());
        mRepository.getMark(id, new Callback<Mark>() {
            @Override
            public void onGetDataSuccess(Mark mark) {
                if (score > mark.getMark()) {
                    mRepository.updateMark(id, score);
                }
                mView.showDialogResult(score, rating);
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }
}
