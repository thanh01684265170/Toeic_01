package com.framgia.toeic.screen.grammar_test;

import com.framgia.toeic.data.model.Grammar;

import java.util.List;

public interface GrammarTestContract {
    interface View {
        void showDialogResult(int mark, int rating);

        void showError(Exception error);
    }

    interface Presenter {
        void checkResult(int id, List<Grammar> grammars);
    }
}
