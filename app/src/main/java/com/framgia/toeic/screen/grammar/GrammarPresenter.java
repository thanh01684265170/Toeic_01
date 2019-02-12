package com.framgia.toeic.screen.grammar;

import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.repository.GrammarLessonRepository;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class GrammarPresenter implements GrammarContract.Presenter {
    private static final int ID_GRAMMAR = 2;
    private GrammarContract.View mView;
    private GrammarLessonRepository mRepository;
    private MarkRepository mMarkRepository;

    public GrammarPresenter(GrammarContract.View view, GrammarLessonRepository repository, MarkRepository markRepository) {
        mView = view;
        mRepository = repository;
        mMarkRepository = markRepository;
    }

    public void setGrammar(final GrammarLesson grammarLesson) {
        mRepository.getGrammars(grammarLesson, new Callback<List<Grammar>>() {
            @Override
            public void onGetDataSuccess(List<Grammar> grammars) {
                grammarLesson.setGrammars(grammars);
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }

    @Override
    public void getGrammarLessons() {
        mRepository.getGrammarLessons(new Callback<List<GrammarLesson>>() {
            @Override
            public void onGetDataSuccess(List<GrammarLesson> grammarLessons) {
                mView.showGrammars(grammarLessons);
                for (GrammarLesson grammarLesson : grammarLessons) {
                    setGrammar(grammarLesson);
                }
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }

    @Override
    public void updateMark(List<GrammarLesson> lessons) {
        int totalMark = 0;
        for (GrammarLesson lesson: lessons){
            totalMark += lesson.getRating();
        }
        mMarkRepository.updateMark(ID_GRAMMAR, totalMark);
    }
}
