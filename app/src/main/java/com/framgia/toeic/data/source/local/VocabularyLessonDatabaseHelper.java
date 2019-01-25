package com.framgia.toeic.data.source.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.toeic.data.VocabularyLessonDataSource;
import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.data.model.VocabularyLessonItem;
import com.framgia.toeic.data.source.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VocabularyLessonDatabaseHelper implements VocabularyLessonDataSource.Local {
    private static final String TABLE_LESSON_VOCABULARY = "tbl_lesson_vocabulary";
    private static final String COLUMN_ID_LESSON = "id";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_VOCABULARY = "tbl_vocabulary";
    private static final String COLUMN_ID_VOCABULARY = "id";
    private static final String COLUMN_WORD_EN = "word_en";
    private static final String COLUMN_WORD_VN = "word_vi";
    private static final String COLUMN_ANSWER_A = "question_a";
    private static final String COLUMN_ANSWER_B = "question_b";
    private static final String COLUMN_ANSWER_C = "question_c";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_COUNT_NUMBER_QUESTION = "count(id)";
    private DBHelper mDBHelper;

    public VocabularyLessonDatabaseHelper(DBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    public void getVocabularyLessons(Callback<List<VocabularyLessonItem>> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        List<VocabularyLessonItem> vocabularyLessonItems = new ArrayList<>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursorLesson = db.query(TABLE_LESSON_VOCABULARY,
                new String[]{COLUMN_ID_LESSON, COLUMN_NAME},
                null, null, null, null, null);
        cursorLesson.moveToFirst();
        do {
            VocabularyLessonItem vocabularyLessonItem;
            vocabularyLessonItem = new VocabularyLessonItem(cursorLesson.getInt(
                    cursorLesson.getColumnIndex(COLUMN_ID_LESSON)),
                    cursorLesson.getString(cursorLesson.getColumnIndex(COLUMN_NAME)), false);
            setVocabularies(vocabularyLessonItem);
            vocabularyLessonItems.add(vocabularyLessonItem);
        } while (cursorLesson.moveToNext());
        callback.onGetDataSuccess(vocabularyLessonItems);
        mDBHelper.close();
    }

    @Override
    public void getNumberQuestionVocabulary(Callback<Integer> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursorNumberQuestion = db.query(TABLE_VOCABULARY,
                new String[]{COLUMN_COUNT_NUMBER_QUESTION},
                null, null, null, null, null);
        cursorNumberQuestion.moveToFirst();
        int numberQuestion = cursorNumberQuestion.getInt(cursorNumberQuestion.getColumnIndex(COLUMN_COUNT_NUMBER_QUESTION));
        callback.onGetDataSuccess(numberQuestion);
        mDBHelper.close();
    }

    public void setVocabularies(final VocabularyLessonItem vocabularyLessonItem) {
        getVocabularies(vocabularyLessonItem, new Callback<List<Vocabulary>>() {
            @Override
            public void onGetDataSuccess(List<Vocabulary> vocabularies) {
                vocabularyLessonItem.setVocabularies(vocabularies);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }

    public void getVocabularies(VocabularyLessonItem vocabularyLessonItem, Callback<List<Vocabulary>> callback) {
        VocabularyLessonItem lesson = vocabularyLessonItem;
        List<Vocabulary> vocabularies = new ArrayList<>();
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursorVocabulary = db.query(TABLE_VOCABULARY,
                null,
                COLUMN_UNIT + "=?",
                new String[]{lesson.getId() + ""},
                null, null, null, null);
        cursorVocabulary.moveToFirst();
        do {
            Vocabulary vocabulary = new Vocabulary.VocabularyBuilder()
                    .setQuestion(cursorVocabulary.getString(cursorVocabulary.getColumnIndex(COLUMN_WORD_EN)))
                    .setResult(cursorVocabulary.getString(cursorVocabulary.getColumnIndex(COLUMN_WORD_VN)))
                    .setAnwserA(cursorVocabulary.getString(cursorVocabulary.getColumnIndex(COLUMN_ANSWER_A)))
                    .setAnwserB(cursorVocabulary.getString(cursorVocabulary.getColumnIndex(COLUMN_ANSWER_B)))
                    .setAnwserC(cursorVocabulary.getString(cursorVocabulary.getColumnIndex(COLUMN_ANSWER_C)))
                    .setId(cursorVocabulary.getInt(cursorVocabulary.getColumnIndex(COLUMN_ID_VOCABULARY)))
                    .setCheck(false).build();
            vocabularies.add(vocabulary);
        } while (cursorVocabulary.moveToNext());
        callback.onGetDataSuccess(vocabularies);
        mDBHelper.close();
    }
}
