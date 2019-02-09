package com.framgia.toeic.data.source.local;

import com.framgia.toeic.data.BasicTestDatasource;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class BasicTestLocalDatasource implements BasicTestDatasource.Local {
    private BasicTestDatabaseHelper mDBHelper;

    public BasicTestLocalDatasource(BasicTestDatabaseHelper DBHelper) {
        mDBHelper = DBHelper;
    }

    @Override
    public void getBasicTestLessons(Callback<List<BasicTestLesson>> callback) {
        mDBHelper.getBasicTestLessons(callback);
    }

    @Override
    public void getBasicTests(BasicTestLesson basicTestLesson, Callback<List<BasicTest>> callback) {
        mDBHelper.getBasicTests(basicTestLesson, callback);
    }
}
