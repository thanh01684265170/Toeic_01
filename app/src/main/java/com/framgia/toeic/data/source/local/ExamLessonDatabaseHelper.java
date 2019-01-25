package com.framgia.toeic.data.source.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.toeic.data.ExamLessonDataSource;
import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.source.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamLessonDatabaseHelper implements ExamLessonDataSource.Local {
    private static final String TABLE_LESSON_EXAM = "tbl_lesson_exam";
    private static final String COLUMN_ID_LESSON = "id";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_EXAM = "tbl_exam";
    private static final String COLUMN_ID_EXAM = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_ANSWER_A = "answer_a";
    private static final String COLUMN_ANSWER_B = "answer_b";
    private static final String COLUMN_ANSWER_C = "answer_c";
    private static final String COLUMN_ANSWER_D = "answer_d";
    private static final String COLUMN_ID_IMAGE = "id_image";
    private static final String COLUMN_UNIT = "id_lesson";
    private DBHelper mDBHelper;

    public ExamLessonDatabaseHelper(DBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    @Override
    public void getExamLessons(Callback<List<ExamLesson>> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        List<ExamLesson> examLessons = new ArrayList<>();
        SQLiteDatabase database = mDBHelper.getReadableDatabase();
        Cursor cursorLesson = database.query(TABLE_LESSON_EXAM,
                new String[]{COLUMN_ID_LESSON, COLUMN_NAME},
                null, null, null, null, null);
        cursorLesson.moveToFirst();
        do {
            ExamLesson examLesson;
            examLesson = new ExamLesson(cursorLesson.getInt(
                    cursorLesson.getColumnIndex(COLUMN_ID_LESSON)),
                    cursorLesson.getString(cursorLesson.getColumnIndex(COLUMN_NAME)));
            examLessons.add(examLesson);
        } while (cursorLesson.moveToNext());
        callback.onGetDataSuccess(examLessons);
        mDBHelper.close();
    }

    public void getExams(ExamLesson examLesson, Callback<List<Exam>> callback) {
        ExamLesson lesson = examLesson;
        List<Exam> exams = new ArrayList<>();
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursorExam = db.query(TABLE_EXAM,
                null, COLUMN_UNIT + "=?",
                new String[]{lesson.getId() + ""},
                null, null, null, null);
        cursorExam.moveToFirst();
        do {
            Exam exam = new Exam.ExamBuilder()
                    .setQuestion(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_QUESTION)))
                    .setResult(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_RESULT)))
                    .setAnwserA(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_A)))
                    .setAnwserB(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_B)))
                    .setAnwserC(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_C)))
                    .setAnwserD(cursorExam.getString(cursorExam.getColumnIndex(COLUMN_ANSWER_D)))
                    .setId(cursorExam.getInt(cursorExam.getColumnIndex(COLUMN_ID_EXAM)))
                    .setIsSelected(false)
                    .setidImage(cursorExam.getInt(cursorExam.getColumnIndex(COLUMN_ID_IMAGE)))
                    .build();
            exams.add(exam);
        } while (cursorExam.moveToNext());
        callback.onGetDataSuccess(exams);
        mDBHelper.close();
    }
}
