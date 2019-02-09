package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.BasicTestDatasource;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class BasicTestRepository implements BasicTestDatasource.Local {
    private BasicTestDatasource.Local mLocal;
    private static BasicTestRepository sBasicTestRepository;

    private BasicTestRepository(BasicTestDatasource.Local local) {
        mLocal = local;
    }

    public static BasicTestRepository getInstance(BasicTestDatasource.Local local) {
        if (sBasicTestRepository == null) {
            sBasicTestRepository = new BasicTestRepository(local);
        } return sBasicTestRepository;
    }

    @Override
    public void getBasicTestLessons(Callback<List<BasicTestLesson>> callback) {
        mLocal.getBasicTestLessons(callback);
    }

    @Override
    public void getBasicTests(BasicTestLesson basicTestLesson, Callback<List<BasicTest>> callback) {
        mLocal.getBasicTests(basicTestLesson, callback);
    }
}
