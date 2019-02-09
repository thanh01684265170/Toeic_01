package com.framgia.toeic.screen.exam;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.data.repository.ExamLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.ExamLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.ExamLessonLocalDataSource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.exam_detail.ExamDetailActivity;

import java.util.List;

public class ExamActivity extends BaseActivity implements ExamContract.View,
        ExamLessonAdapter.OnItemClickListener {
    private ExamLessonAdapter mLessonAdapter;
    private ExamContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    public static Intent getExamActivity(Context context) {
        return new Intent(context, ExamActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_exam;
    }

    @Override
    protected void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_grammar));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor
                (R.color.material_cyan_300)));
        mRecyclerView = findViewById(R.id.recycler_exam);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mPresenter = new ExamPresenter(this, ExamLessonRepository.getInstance(
                new ExamLessonLocalDataSource(new ExamLessonDatabaseHelper(new DBHelper(this)))));
        mPresenter.getExams();
    }

    @Override
    public void showExams(List<ExamLesson> examLessons) {
        mLessonAdapter = new ExamLessonAdapter(examLessons, this);
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mLessonAdapter);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(ExamLesson examLesson) {
        startActivity(ExamDetailActivity.getIntent(this,examLesson));
    }
}
