package com.framgia.toeic.screen.vocabulary_detail;

import com.framgia.toeic.data.model.Vocabulary;

import java.util.List;

public interface VocabularyDetailContract {
    interface View {
        void showDialogResult(int mark, int rating);
        void showError(Exception error);
    }

    interface Presenter {
        void checkResult(int id, List<Vocabulary> vocabularies);
    }
}
