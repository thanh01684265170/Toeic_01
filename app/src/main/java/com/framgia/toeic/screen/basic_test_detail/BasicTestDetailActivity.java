package com.framgia.toeic.screen.basic_test_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.local.BasicTestDatabaseHelper;
import com.framgia.toeic.data.source.local.BasicTestLocalDatasource;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.screen.base.ResultTest;

import java.util.List;

public class BasicTestDetailActivity extends ResultTest implements BasicTestDetailContract.View {
    private static final String EXTRA_BASIC_TEST_LESSON = "EXTRA_BASIC_TEST_LESSON";
    private RecyclerView mRecyclerBasicTest;
    private TextView mTextViewTime;
    private TextView mTextViewSubmit;
    private BasicTestLesson mLesson;
    private BasicTestDetailAdapter mAdapter;
    private BasicTestDetailContract.Presenter mPresenter;

    public static Intent getIntent(Context context, BasicTestLesson basicTestLesson) {
        Intent intent = new Intent(context, BasicTestDetailActivity.class);
        intent.putExtra(EXTRA_BASIC_TEST_LESSON, basicTestLesson);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_basic_test_detail;
    }

    @Override
    protected void initComponent() {
        mRecyclerBasicTest = findViewById(R.id.recycler_basic_test);
        mTextViewSubmit = findViewById(R.id.text_submit);
        mTextViewTime = findViewById(R.id.text_timer);
        mPresenter = new BasicTestDetailPresenter(this, BasicTestRepository
                .getInstance(new BasicTestLocalDatasource(new BasicTestDatabaseHelper(
                        new DBHelper(this)))));
    }

    @Override
    protected void initData() {
        mLesson = getIntent().getParcelableExtra(EXTRA_BASIC_TEST_LESSON);
        mPresenter.getBasicTest(mLesson);
        mTextViewSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_submit:
                mPresenter.checkAnswer(mLesson.getBasicTests());
                mAdapter.setChecked(true);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void showBasicTest(List<BasicTest> basicTests) {
        mLesson.setBasicTests(basicTests);
        mAdapter = new BasicTestDetailAdapter(this, basicTests, false);
        mRecyclerBasicTest.setAdapter(mAdapter);
    }

    @Override
    public void listenMedia() {

    }

    @Override
    public void pauseMedia() {

    }

    @Override
    public void showDialogResult(int mark, int rating) {
        super.showDialogResult(mark, rating);
        mTextViewFalse.setText(mLesson.getBasicTests().size() - mark + "");
    }
}
