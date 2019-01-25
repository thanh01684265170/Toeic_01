package com.framgia.toeic.screen.grammar;

import com.framgia.toeic.data.model.GrammarLesson;

import java.util.List;

public interface GrammarContract {
    interface View {
        void showGrammars(List<GrammarLesson> grammarLessons);

        void showError(Exception e);
    }

    interface Presenter {
        void getGrammarLessons();
    }
}
