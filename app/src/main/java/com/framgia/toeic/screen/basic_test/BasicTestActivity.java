package com.framgia.toeic.screen.basic_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTestLesson;
import com.framgia.toeic.data.repository.BasicTestRepository;
import com.framgia.toeic.data.source.local.BasicTestDatabaseHelper;
import com.framgia.toeic.data.source.local.BasicTestLocalDatasource;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.basic_test_detail.BasicTestDetailActivity;

import java.util.List;

public class BasicTestActivity extends BaseActivity
        implements BasicTestContract.View, BasicTestAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private BasicTestContract.Presenter mPresenter;

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, BasicTestActivity.class);
        return intent;
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
        return R.layout.activity_basic_test;
    }

    @Override
    protected void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.action_basic_test));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor
                (R.color.material_cyan_300)));
        mRecyclerView = findViewById(R.id.recycle_basic_test);
        mPresenter = new BasicTestPresenter(this,
                BasicTestRepository.getInstance(
                        new BasicTestLocalDatasource(new BasicTestDatabaseHelper(
                                new DBHelper(this)))));
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getBasicTestLessons();
    }

    @Override
    public void showBasicTestLesson(List<BasicTestLesson> lessons) {
        BasicTestAdapter adapter = new BasicTestAdapter(lessons, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(BasicTestLesson basicTestLesson) {
        startActivity(BasicTestDetailActivity.getIntent(this, basicTestLesson));
    }
}
