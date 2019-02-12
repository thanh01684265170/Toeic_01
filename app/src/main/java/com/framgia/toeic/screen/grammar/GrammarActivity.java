package com.framgia.toeic.screen.grammar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.data.repository.GrammarLessonRepository;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.GrammarLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.GrammarLessonLocalDataSource;
import com.framgia.toeic.data.source.local.MarkDatabaseHelper;
import com.framgia.toeic.data.source.local.MarkLocalDataSource;
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
        Window window = getWindow();
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.material_accent_700));
        }
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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor
                (R.color.material_cyan_300)));
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
                new GrammarLessonLocalDataSource(new GrammarLessonDatabaseHelper(new DBHelper(this)))),
                MarkRepository.getInstance(new MarkLocalDataSource(new MarkDatabaseHelper(new DBHelper(this)))));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getGrammarLessons();
    }

    @Override
    public void showGrammars(List<GrammarLesson> grammarLessons) {
        mLessonAdapter = new GrammarLessonAdapter(grammarLessons, this);
        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mLessonAdapter);
        mPresenter.updateMark(grammarLessons);
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
