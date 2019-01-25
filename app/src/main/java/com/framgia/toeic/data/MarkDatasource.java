package com.framgia.toeic.data;

import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public interface MarkDatasource {
    interface Local{
        void getMarks(Callback<List<Mark>> callback);
        void getMark(int id, Callback<Mark> callback);
        void updateMark(int id, int mark);
    }

    interface Remote{

    }
}
