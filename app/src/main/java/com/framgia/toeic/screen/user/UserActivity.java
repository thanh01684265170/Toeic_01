package com.framgia.toeic.screen.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.framgia.toeic.R;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.vocabulary.VocabularyActivity;

public class UserActivity extends BaseActivity {
    public static Intent getUserIntent(Context context) {
        return new Intent(context, UserActivity.class);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user;
    }

    @Override
    protected void initComponent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Mục tiêu: 600");
    }

    @Override
    protected void initData() {

    }
}
