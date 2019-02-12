package com.framgia.toeic.data.source.local;

import com.framgia.toeic.data.VocabularyLessonDataSource;
import com.framgia.toeic.data.model.VocabularyLessonItem;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class VocabularyLessonLocalDataSource implements VocabularyLessonDataSource.Local {
    private VocabularyLessonDatabaseHelper mDatabaseHelper;

    public VocabularyLessonLocalDataSource(VocabularyLessonDatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void getVocabularyLessons(Callback<List<VocabularyLessonItem>> callback) {
        mDatabaseHelper.getVocabularyLessons(callback);
    }
}
