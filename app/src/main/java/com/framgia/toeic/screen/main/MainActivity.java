package com.framgia.toeic.screen.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.toeic.R;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.repository.VocabularyLessonRepository;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.source.local.MarkDatabaseHelper;
import com.framgia.toeic.data.source.local.MarkLocalDataSource;
import com.framgia.toeic.data.source.local.VocabularyLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.VocabularyLessonLocalDataSource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.basic_test.BasicTestActivity;
import com.framgia.toeic.screen.exam.ExamActivity;
import com.framgia.toeic.screen.grammar.GrammarActivity;
import com.framgia.toeic.screen.user.UserActivity;
import com.framgia.toeic.screen.vocabulary.VocabularyActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, MainContract.View {
    private static final String PREFNAME = "data_user";
    private static final String NAME = "name";
    private static final String TARGET = "target";
    private static final String DEFAULT_NAME = "Tên của bạn";
    private static final String DEFAULT_TARGET = "600";
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private CardView mCardVocabulary, mCardGrammar, mCardBasicTest, mCardExam, mCardUser;
    private MainContract.Presenter mPresenter;
    private SharedPreferences mPreferences;
    private TextView mTextName;
    private TextView mTextTarget;

    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.marterial_indigo_500));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoringPreferences();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setBackgroundColor(getResources().getColor(R.color.marterial_deep_purple_200));
        mCardVocabulary = findViewById(R.id.card_vocabulary);
        mCardBasicTest = findViewById(R.id.card_basic_test);
        mCardGrammar = findViewById(R.id.card_grammar);
        mCardExam = findViewById(R.id.card_exam);
        mCardUser = findViewById(R.id.card_user);
        mPresenter = new MainPresenter(MarkRepository.getInstance(
                new MarkLocalDataSource(new MarkDatabaseHelper(new DBHelper(this)))),
                VocabularyLessonRepository.getInstance(new VocabularyLessonLocalDataSource(
                        new VocabularyLessonDatabaseHelper(new DBHelper(this)))), this);
        mPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        mTextName = findViewById(R.id.text_name);
        mTextTarget = findViewById(R.id.text_target);
    }

    @Override
    protected void initData() {
        getSupportActionBar().setBackgroundDrawable(new
                ColorDrawable(getResources().getColor(R.color.marterial_indigo_400)));
        mCardVocabulary.setOnClickListener(this);
        mCardGrammar.setOnClickListener(this);
        mCardBasicTest.setOnClickListener(this);
        mCardExam.setOnClickListener(this);
        mCardUser.setOnClickListener(this);
        mPresenter.getNumberQuestion();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_vocabulary:
                startActivity(VocabularyActivity.getVocabularyIntent(this));
                break;

            case R.id.nav_grammar:
                startActivity(GrammarActivity.getGrammarIntent(this));
                break;

            case R.id.nav_basic_test:
                startActivity(BasicTestActivity.getIntent(this));
                break;

            case R.id.nav_exam:
                startActivity(ExamActivity.getExamActivity(this));
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_setting:
                break;

            case R.id.nav_rating:
                break;

            case R.id.nav_about:
                break;

        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getMarks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_vocabulary:
                startActivity(VocabularyActivity.getVocabularyIntent(this));
                break;

            case R.id.card_grammar:
                startActivity(GrammarActivity.getGrammarIntent(this));
                break;

            case R.id.card_basic_test:
                startActivity(BasicTestActivity.getIntent(this));
                break;

            case R.id.card_exam:
                startActivity(ExamActivity.getExamActivity(this));
                break;
            case R.id.card_user:
                startActivity(UserActivity.getUserIntent(this));
                break;
        }
    }

    @Override
    public void showError(Exception error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void restoringPreferences() {
        String name = mPreferences.getString(NAME, DEFAULT_NAME);
        String target = mPreferences.getString(TARGET, DEFAULT_TARGET);
        mTextName.setText(name);
        mTextTarget.setText(target);
    }
}
