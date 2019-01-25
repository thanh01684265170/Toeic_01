package com.framgia.toeic.screen.main;

import android.util.Log;

import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.repository.VocabularyLessonRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MarkRepository mMarkRepository;
    private VocabularyLessonRepository mVocabularyLessonRepository;
    private MainContract.View mView;
    public MainPresenter(MarkRepository repository,
                         VocabularyLessonRepository vocabularyLessonRepository,
                         MainContract.View view) {
        mMarkRepository = repository;
        mVocabularyLessonRepository = vocabularyLessonRepository;
        mView = view;
    }

    @Override
    public void getMarks() {
        mMarkRepository.getMarks(new Callback<List<Mark>>() {
            @Override
            public void onGetDataSuccess(List<Mark> marks) {
                mView.showValueProgressBar(marks);
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });
    }

    @Override
    public void getNumberQuestion() {
        final List<Integer> numberQuestions = new ArrayList<>();
        mVocabularyLessonRepository.getNumberQuestionVocabulary(new Callback<Integer>() {
            @Override
            public void onGetDataSuccess(Integer integer) {
                numberQuestions.add(integer);
            }

            @Override
            public void onGetDataFail(Exception error) {
                mView.showError(error);
            }
        });

        mView.setMaxSizeProgressBars(numberQuestions);
    }


}
