package com.framgia.toeic.screen.exam_detail;

import android.content.Context;
import android.content.Intent;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.screen.base.BaseActivity;

public class ExamDetailActivity extends BaseActivity {
    static final String EXTRA_EXAM_LESSON = "EXTRA_EXAM_LESSON";
    private ExamLesson mLesson;

    public static Intent getIntent(Context context, ExamLesson examLesson) {
        Intent intent = new Intent(context, ExamDetailActivity.class);
        intent.putExtra(EXTRA_EXAM_LESSON, examLesson);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_exam_detail;
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        mLesson = getIntent().getExtras().getParcelable(EXTRA_EXAM_LESSON);
    }
}
