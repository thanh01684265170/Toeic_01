package com.framgia.toeic.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.framgia.toeic.data.BasicTestDatasource;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.source.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicTestDatabaseHelper implements BasicTestDatasource.Local {
    private static final String TABLE_LESSON_BASIC_TEST = "tbl_lesson_basic_test";
    private static final String COLUMN_ID_LESSON = "id";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_BASIC_TEST = "tbl_basic";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_ANSWER_A = "answer_a";
    private static final String COLUMN_ANSWER_B = "answer_b";
    private static final String COLUMN_ANSWER_C = "answer_c";
    private static final String COLUMN_ANSWER_D = "answer_d";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_UNIT_BASIC_TEST = "id_lesson_exam";
    private static final String COLUMN_PART = "part";
    private static final String COLUMN_MAX_MARK = "max_mark";
    private DBHelper mDBHelper;

    public BasicTestDatabaseHelper(DBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    @Override
    public void getBasicTestLessons(Callback<List<BasicTestLesson>> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        List<BasicTestLesson> basicTestLessons = new ArrayList<>();
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursorLesson = database.query(TABLE_LESSON_BASIC_TEST,
                null,null, null, null, null, null);
        cursorLesson.moveToFirst();
        do {
            BasicTestLesson basicTestLesson;
            basicTestLesson = new BasicTestLesson(
                    cursorLesson.getInt(cursorLesson.getColumnIndex(COLUMN_ID_LESSON)),
                    cursorLesson.getString(cursorLesson.getColumnIndex(COLUMN_NAME)),
                    cursorLesson.getInt(cursorLesson.getColumnIndex(COLUMN_MAX_MARK)));
            basicTestLessons.add(basicTestLesson);
        } while (cursorLesson.moveToNext());
        callback.onGetDataSuccess(basicTestLessons);
        mDBHelper.close();
    }

    @Override
    public void getBasicTests(BasicTestLesson basicTestLesson, Callback<List<BasicTest>> callback) {
        List<BasicTest> basicTests = new ArrayList<>();
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursorExam = db.query(TABLE_BASIC_TEST,
                null, COLUMN_UNIT_BASIC_TEST + "=?",
                new String[]{basicTestLesson.getId() + ""},
                null, null, null, null);
        cursorExam.moveToFirst();
        do {
            BasicTest basicTest = new BasicTest.Builder()
                    .setQuestion(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_QUESTION)))
                    .setResult(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_RESULT)))
                    .setAnwserA(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_A)))
                    .setAnwserB(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_B)))
                    .setAnwserC(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_C)))
                    .setAnwserD(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_D)))
                    .setId(cursorExam.getInt(cursorExam.getColumnIndex(COLUMN_ID_LESSON)))
                    .setIsSelected(false)
                    .setidImage(cursorExam.getInt(cursorExam.getColumnIndex(COLUMN_IMAGE)))
                    .setPart(cursorExam.getInt(cursorExam.getColumnIndex(COLUMN_PART)))
                    .build();
            basicTests.add(basicTest);
        } while (cursorExam.moveToNext());
        callback.onGetDataSuccess(basicTests);
        mDBHelper.close();
    }

    @Override
    public void updateBasicTestLesson(BasicTestLesson lesson, int mark, Callback<BasicTestLesson> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MAX_MARK, mark);
        db.update(TABLE_LESSON_BASIC_TEST, contentValues, COLUMN_ID_LESSON + "=?",
                new String[]{lesson.getId() + ""});
    }
}
