package com.framgia.toeic.screen.grammar_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.GrammarLesson;
import com.framgia.toeic.screen.base.BaseActivity;
import com.framgia.toeic.screen.grammar_test.GrammarTestActivity;
import com.github.barteksc.pdfviewer.PDFView;

public class GrammarDetailAcvitity extends BaseActivity {
    static final String EXTRA_LESSON = "EXTRA_LESSON";
    private PDFView mPDFView;
    private GrammarLesson mLesson;
    public static Intent getIntent(Context context, GrammarLesson grammarLesson) {
        Intent intent = new Intent(context, GrammarDetailAcvitity.class);
        intent.putExtra(EXTRA_LESSON, grammarLesson);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_grammar_detail_acvitity;
    }

    @Override
    protected void initComponent() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mPDFView = findViewById(R.id.pdf_grammar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grammar_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_next:
                startActivity(GrammarTestActivity.getIntent(this, mLesson.getGrammars()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mLesson = getIntent().getExtras().getParcelable(EXTRA_LESSON);
        mPDFView.fromAsset(mLesson.getName() + getResources().getString(R.string.file))
                .enableAnnotationRendering(false)
                .enableSwipe(true)
                .load();
    }
}
