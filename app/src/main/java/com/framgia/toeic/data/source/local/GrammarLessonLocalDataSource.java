package com.framgia.toeic.data.source.local;

import com.framgia.toeic.data.GrammarLessonDataSource;
import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class GrammarLessonLocalDataSource implements GrammarLessonDataSource.Local {
    private GrammarLessonDatabaseHelper mDatabaseHelper;

    public GrammarLessonLocalDataSource(GrammarLessonDatabaseHelper databaseHelper) {
        mDatabaseHelper = databaseHelper;
    }

    @Override
    public void getGrammarLessons(Callback<List<GrammarLesson>> callback) {
        mDatabaseHelper.getGrammarLessons(callback);
    }

    @Override
    public void getGrammars(GrammarLesson grammarLesson, Callback<List<Grammar>> callback) {
        mDatabaseHelper.getGrammars(grammarLesson, callback);
    }
}
