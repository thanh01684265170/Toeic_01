package com.framgia.toeic.data.source.local;

import com.framgia.toeic.data.MarkDatasource;
import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class MarkLocalDataSource implements MarkDatasource.Local {
    private MarkDatabaseHelper mHelper;

    public MarkLocalDataSource(MarkDatabaseHelper helper) {
        mHelper = helper;
    }

    @Override
    public void getMarks(Callback<List<Mark>> callback) {
        mHelper.getMarks(callback);
    }

    @Override
    public void getMark(int id, Callback<Mark> callback) {
        mHelper.getMark(id, callback);
    }

    @Override
    public void updateMark(int id, int mark) {
        mHelper.updateMark(id, mark);
    }

    @Override
    public void getMaxMark(Callback<List<Integer>> callback) {
        mHelper.getMaxMark(callback);
    }
}
