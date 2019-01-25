package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.GrammarLessonDataSource;
import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class GrammarLessonRepository implements GrammarLessonDataSource.Local {
    public static GrammarLessonRepository sGrammarLessonRepository;
    private GrammarLessonDataSource.Local mLocal;

    public GrammarLessonRepository(GrammarLessonDataSource.Local local) {
        mLocal = local;
    }

    public static GrammarLessonRepository getInstance(GrammarLessonDataSource.Local local) {
        if (sGrammarLessonRepository == null) {
            sGrammarLessonRepository = new GrammarLessonRepository(local);
        }
        return sGrammarLessonRepository;
    }

    @Override
    public void getGrammarLessons(Callback<List<GrammarLesson>> callback) {
        mLocal.getGrammarLessons(callback);
    }

    @Override
    public void getGrammars(GrammarLesson grammarLesson, Callback<List<Grammar>> callback) {
        mLocal.getGrammars(grammarLesson, callback);
    }
}
