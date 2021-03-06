package com.framgia.toeic.screen.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.data.model.VocabularyLessonItem;
import com.framgia.toeic.data.repository.VocabularyLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.VocabularyLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.VocabularyLessonLocalDataSource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.vocabulary_detail.VocabularyDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VocabularyActivity extends BaseActivity implements View.OnClickListener, VocabularyContract.View {
    private VocabularyLessonAdapter mVocabularyLessonAdapter;
    private RecyclerView mRecyclerView;
    private VocabularyContract.Presenter mPresenter;

    public static Intent getVocabularyIntent(Context context) {
        return new Intent(context, VocabularyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        return R.layout.activity_vocabulary;
    }

    @Override
    protected void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_vocabulary));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor
                (R.color.material_cyan_300)));
        findViewById(R.id.fab_next).setOnClickListener(this);
        mRecyclerView = findViewById(R.id.recycler_vocabulary_lesson);
    }

    @Override
    protected void initData() {
        mPresenter = new VocabularyPresenter(this,
                VocabularyLessonRepository.getInstance(new VocabularyLessonLocalDataSource(
                        new VocabularyLessonDatabaseHelper(new DBHelper(this)))));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getVocabularies();
    }

    @Override
    public void onClick(View view) {
        mPresenter.pushVocabularies();
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
    public void showVocabularies(List<VocabularyLessonItem> vocabularyLessonItems) {
        mVocabularyLessonAdapter = new VocabularyLessonAdapter(this, vocabularyLessonItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mVocabularyLessonAdapter);
    }

    @Override
    public void showVocabularyDeatailActivity(List<VocabularyLessonItem> vocabularyLessonItems) {
        List<Vocabulary> vocabularies = new ArrayList<>();
        for (VocabularyLessonItem vocabularyLessonItem : vocabularyLessonItems) {
            vocabularies.addAll(vocabularyLessonItem.getVocabularies());
        }
        Collections.shuffle(vocabularies);
        startActivity(VocabularyDetailActivity.getIntent(this, vocabularies));
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotificationEmpty() {
        Toast.makeText(this, getResources().getString(R.string.notification_empty), Toast.LENGTH_SHORT).show();
    }
}
