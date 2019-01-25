package com.framgia.toeic.screen.vocabulary;

import com.framgia.toeic.data.model.VocabularyLessonItem;
import com.framgia.toeic.data.repository.VocabularyLessonRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class VocabularyPresenter implements VocabularyContract.Presenter {
    private VocabularyLessonRepository mRepository;
    private VocabularyContract.View mView;
    private List<VocabularyLessonItem> mVocabularyLessonItems;

    public VocabularyPresenter(VocabularyContract.View view, VocabularyLessonRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void getVocabularies() {
        mRepository.getVocabularyLessons(new Callback<List<VocabularyLessonItem>>() {
            @Override
            public void onGetDataSuccess(List<VocabularyLessonItem> vocabularyLessonItems) {
                mVocabularyLessonItems = vocabularyLessonItems;
                mView.showVocabularies(vocabularyLessonItems);
            }

            @Override
            public void onGetDataFail(Exception e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void pushVocabularies() {
        mView.showVocabularyDeatailActivity(mVocabularyLessonItems);
    }
}
