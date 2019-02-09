package com.framgia.toeic.data;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public interface BasicTestDatasource {
    interface Local{
        void getBasicTestLessons(Callback<List<BasicTestLesson>> callback);

        void getBasicTests(BasicTestLesson basicTestLesson, Callback<List<BasicTest>> callback);    }

    interface Remote{

    }
}
