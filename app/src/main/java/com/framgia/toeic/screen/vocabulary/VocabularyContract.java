package com.framgia.toeic.screen.vocabulary;

import com.framgia.toeic.data.model.VocabularyLessonItem;

import java.util.List;

public interface VocabularyContract {
    interface View {
        void showVocabularies(List<VocabularyLessonItem> vocabularyLessonItems);

        void showVocabularyDeatailActivity(List<VocabularyLessonItem> vocabularyLessonItems);

        void showError(Exception e);
    }

    interface Presenter {
        void getVocabularies();

        void pushVocabularies();
    }
}
