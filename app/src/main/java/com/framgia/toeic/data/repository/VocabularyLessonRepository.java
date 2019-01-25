package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.VocabularyLessonDataSource;
import com.framgia.toeic.data.model.VocabularyLessonItem;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class VocabularyLessonRepository implements VocabularyLessonDataSource.Local {
    public static VocabularyLessonRepository sVocabularyLessonRepository;
    private VocabularyLessonDataSource.Local mVocabularyLessonDataSource;

    public VocabularyLessonRepository(VocabularyLessonDataSource.Local vocabularyLessonDataSource) {
        mVocabularyLessonDataSource = vocabularyLessonDataSource;
    }

    public static VocabularyLessonRepository getInstance(VocabularyLessonDataSource.Local local) {
        if (sVocabularyLessonRepository == null) {
            sVocabularyLessonRepository = new VocabularyLessonRepository(local);
        }
        return sVocabularyLessonRepository;
    }

    @Override
    public void getVocabularyLessons(Callback<List<VocabularyLessonItem>> callback) {
        mVocabularyLessonDataSource.getVocabularyLessons(callback);
    }

    @Override
    public void getNumberQuestionVocabulary(Callback<Integer> callback) {
        mVocabularyLessonDataSource.getNumberQuestionVocabulary(callback);
    }
}
