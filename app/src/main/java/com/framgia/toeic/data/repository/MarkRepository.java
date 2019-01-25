package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.MarkDatasource;
import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class MarkRepository implements MarkDatasource.Local {
    private MarkDatasource.Local mLocal;
    private static MarkRepository sMarkRepository;

    public static MarkRepository getInstance(MarkDatasource.Local local) {
        if (sMarkRepository == null) {
            sMarkRepository = new MarkRepository(local);
        }
        return sMarkRepository;
    }

    public MarkRepository(MarkDatasource.Local local) {
        mLocal = local;
    }

    @Override
    public void getMarks(Callback<List<Mark>> callback) {
        mLocal.getMarks(callback);
    }

    @Override
    public void getMark(int id, Callback<Mark> callback) {
        mLocal.getMark(id, callback);
    }

    @Override
    public void updateMark(int id, int mark) {
        mLocal.updateMark(id, mark);
    }
}
