package com.framgia.toeic.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.framgia.toeic.data.MarkDatasource;
import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.source.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarkDatabaseHelper implements MarkDatasource.Local {
    private static final String TABLE_MARK = "tbl_mark";
    private static final String TABLE_VOCABULARY = "tbl_vocabulary";
    private static final String TABLE_GRAMMAR = "tbl_grammar";
    private static final String TABLE_BASIC_TEST = "tbl_basic";
    private static final String TABLE_EXAM = "tbl_exam";
    private static final String COLUMN_ID_MARK = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARK = "max_mark";
    private static final String COUNT_ID = "COUNT(id)";
    private DBHelper mDBHelper;

    public MarkDatabaseHelper(DBHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    @Override
    public void getMarks(Callback<List<Mark>> callback) {
        List<Mark> marks = new ArrayList<>();
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        Cursor cursorMark = db.query(TABLE_MARK, null, null,
                null, null, null, null, null);
        cursorMark.moveToFirst();
        do {
            Mark mark = new Mark(cursorMark.getInt(
                    cursorMark.getColumnIndex(COLUMN_ID_MARK)),
                    cursorMark.getString(cursorMark.getColumnIndex(COLUMN_NAME)),
                    cursorMark.getInt(cursorMark.getColumnIndex(COLUMN_MARK)));
            marks.add(mark);
        } while (cursorMark.moveToNext());
        callback.onGetDataSuccess(marks);
        mDBHelper.close();
    }

    @Override
    public void getMark(int id, Callback<Mark> callback) {
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursorMark = db.query(TABLE_MARK, null, COLUMN_ID_MARK + "=?",
                new String[]{id + ""}, null, null, null);
        cursorMark.moveToFirst();
        Mark mark = new Mark(cursorMark.getInt(
                cursorMark.getColumnIndex(COLUMN_ID_MARK)),
                cursorMark.getString(cursorMark.getColumnIndex(COLUMN_NAME)),
                cursorMark.getInt(cursorMark.getColumnIndex(COLUMN_MARK)));
        callback.onGetDataSuccess(mark);
        mDBHelper.close();
    }

    @Override
    public void updateMark(int id, int mark) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MARK, mark);
        db.update(TABLE_MARK, contentValues, COLUMN_ID_MARK + "=?", new String[]{id + ""});
    }

    @Override
    public void getMaxMark(Callback<List<Integer>> callback) {
        List<String> modules = Arrays.asList(TABLE_VOCABULARY, TABLE_GRAMMAR, TABLE_BASIC_TEST, TABLE_EXAM);
        List<Integer> values = new ArrayList<>();
        try {
            mDBHelper.openDatabase();
        } catch (IOException e) {
            callback.onGetDataFail(e);
            return;
        }

        for (String module : modules) {
            SQLiteDatabase db = mDBHelper.getReadableDatabase();
            Cursor cursorMark = db.query(module, new String[]{COUNT_ID},
                    null, null, null, null, null);
            cursorMark.moveToFirst();
            int value = cursorMark.getInt(cursorMark.getColumnIndex(COUNT_ID));
            values.add(value);
        }
        callback.onGetDataSuccess(values);
    }
}
