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
import java.util.List;

public class MarkDatabaseHelper implements MarkDatasource.Local {
    private static final String TABLE_MARK = "tbl_mark";
    private static final String COLUMN_ID_MARK = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARK = "max_mark";
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
                new String[]{id+""}, null, null, null);
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
}
