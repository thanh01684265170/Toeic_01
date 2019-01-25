package com.framgia.toeic.screen.grammar_test;

import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;
import com.framgia.toeic.screen.base.RatingCaculator;

import java.util.List;

public class GrammarTestPresenter extends RatingCaculator implements GrammarTestContract.Presenter {
    private GrammarTestContract.View mView;
    private MarkRepository mRepository;

    public GrammarTestPresenter(GrammarTestContract.View view, MarkRepository repository) {
        mView = view;
        mRepository = repository;
    }

    public int caculateScore(List<Grammar> grammars) {
        int score = 0;
        for (Grammar grammar : grammars) {
            if (grammar.isSelected()) {
                score++;
            }
        }
        return score;
    }

    @Override
    public void checkResult(final int id, List<Grammar> grammars) {
        final int score = caculateScore(grammars);
        final int rating = rating(score, grammars.size());
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
