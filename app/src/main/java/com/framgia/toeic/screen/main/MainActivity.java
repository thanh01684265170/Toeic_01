package com.framgia.toeic.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.framgia.toeic.R;
import com.framgia.toeic.data.source.local.DBHelper;
import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.repository.VocabularyLessonRepository;
import com.framgia.toeic.data.source.local.MarkDatabaseHelper;
import com.framgia.toeic.data.source.local.MarkLocalDataSource;
import com.framgia.toeic.data.source.local.VocabularyLessonDatabaseHelper;
import com.framgia.toeic.data.source.local.VocabularyLessonLocalDataSource;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.exam.ExamActivity;
import com.framgia.toeic.screen.grammar.GrammarActivity;
import com.framgia.toeic.screen.vocabulary.VocabularyActivity;

import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, MainContract.View {
    private static final int ID_VOCABULARY = 0;
    private static final int ID_GRAMMAR = 1;
    private static final int ID_BASIC_TEST = 2;
    private static final int ID_EXAM = 3;
    private static final int BACKGROUNDS_INTRO[] = {R.drawable.bg_intro_1,
            R.drawable.bg_intro_2, R.drawable.bg_intro_3, R.drawable.bg_intro_4};
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewFlipper mViewFlipper;
    private ProgressBar mProgressVocabulary;
    private ProgressBar mProgressGrammar;
    private ProgressBar mProgressBasicTest;
    private ProgressBar mProgressExam;
    private DrawerLayout mDrawer;
    private Button mButtonVocabulary, mButtonGrammar, mButtonBasicTest, mButtonExam;
    private MainContract.Presenter mPresenter;

    public static Intent getMainIntent(Context context){
        return new Intent(context,MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mViewFlipper = findViewById(R.id.viewflipper);
        mProgressVocabulary = findViewById(R.id.progress_vocabulary);
        mProgressGrammar = findViewById(R.id.progress_grammar);
        mProgressBasicTest = findViewById(R.id.progress_basic_test);
        mProgressExam = findViewById(R.id.progress_exam);
        mButtonVocabulary = findViewById(R.id.button_vocabulary);
        mButtonGrammar = findViewById(R.id.button_grammar);
        mButtonBasicTest = findViewById(R.id.button_basic_test);
        mButtonExam = findViewById(R.id.button_exam);
        mPresenter = new MainPresenter(MarkRepository.getInstance(
                new MarkLocalDataSource(new MarkDatabaseHelper(new DBHelper(this)))),
                VocabularyLessonRepository.getInstance(new VocabularyLessonLocalDataSource(
                        new VocabularyLessonDatabaseHelper(new DBHelper(this)))), this);
    }

    @Override
    protected void initData() {
        slideAdvertise();
        mProgressVocabulary.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.material_orange_300),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mProgressGrammar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.material_brown_300),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mProgressBasicTest.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.material_blue_accent_400),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mProgressExam.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.material_red_accent_200),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        mButtonVocabulary.setOnClickListener(this);
        mButtonGrammar.setOnClickListener(this);
        mButtonBasicTest.setOnClickListener(this);
        mButtonExam.setOnClickListener(this);
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
            case R.id.button_vocabulary:
                startActivity(VocabularyActivity.getVocabularyIntent(this));
                break;

            case R.id.button_grammar:
                startActivity(GrammarActivity.getGrammarIntent(this));
                break;

            case R.id.button_basic_test:
                break;

            case R.id.button_exam:
                startActivity(ExamActivity.getExamActivity(this));
                break;
        }
    }

    private void slideAdvertise() {
        for (int image : BACKGROUNDS_INTRO) {
            flipperImage(image);
        }
    }

    private void flipperImage(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        mViewFlipper.addView(imageView);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void showValueProgressBar(List<Mark> marks) {
        mProgressVocabulary.setProgress(marks.get(ID_VOCABULARY).getMark());
        mProgressGrammar.setProgress(marks.get(ID_GRAMMAR).getMark());
        mProgressBasicTest.setProgress(marks.get(ID_BASIC_TEST).getMark());
        mProgressExam.setProgress(marks.get(ID_EXAM).getMark());
    }

    @Override
    public void setMaxSizeProgressBars(List<Integer> integers) {
        mProgressVocabulary.setMax(integers.get(ID_VOCABULARY));
    }

    @Override
    public void showError(Exception error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
