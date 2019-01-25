package com.framgia.toeic.screen.grammar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.repository.GrammarLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.GrammarLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.GrammarLessonLocalDataSource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.grammar_detail.GrammarDetailAcvitity;

import java.util.List;

public class GrammarActivity extends BaseActivity implements GrammarContract.View,
        GrammarLessonAdapter.OnItemClickListener {
    private GrammarLessonAdapter mLessonAdapter;
    private GrammarContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    public static Intent getGrammarIntent(Context context) {
        return new Intent(context, GrammarActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_grammar;
    }

    @Override
    protected void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_grammar));
        mRecyclerView = findViewById(R.id.recycle_lesson_grammar);
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
        mPresenter = new GrammarPresenter(this, GrammarLessonRepository.getInstance(
                new GrammarLessonLocalDataSource(new GrammarLessonDatabaseHelper(new DBHelper(this)))));
        mPresenter.getGrammarLessons();
    }

    @Override
    public void showGrammars(List<GrammarLesson> grammarLessons) {
        mLessonAdapter = new GrammarLessonAdapter(grammarLessons, this);
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mLessonAdapter);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(GrammarLesson grammarLesson) {
        startActivity(GrammarDetailAcvitity.getIntent(this, grammarLesson));
    }
}
