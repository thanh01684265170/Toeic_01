package com.framgia.toeic.screen.basic_test_detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.data.model.Exam;
import com.framgia.toeic.screen.exam_detail.ExamAdapter;

import java.util.List;

public class BasicTestDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_1 = 2;
    private static final int VIEW = 1;
    private static final int VIEW_2 = 3;
    private static final String EXTENSION_PNG = ".PNG";
    private Context mContext;
    private List<BasicTest> mBasicTests;
    private boolean isChecked;

    public BasicTestDetailAdapter(Context context, List<BasicTest> basicTests, boolean isChecked) {
        mContext = context;
        mBasicTests = basicTests;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 2){
            return VIEW;
        }

        if (position < 4){
            return VIEW_1;
        }

        return VIEW_2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case VIEW:
                return new BasicTestDetailAdapter.ViewHolderType1(
                        LayoutInflater.from(mContext).inflate(R.layout.item_exam_question_type_1,
                                viewGroup, false));
            case VIEW_1:
                return new BasicTestDetailAdapter.ViewHolderType2(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_exam_question_type_2, viewGroup, false));
            default:
                return new BasicTestDetailAdapter.ViewHolderType3(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_exam_question_type_3, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case VIEW:
                ExamAdapter.ViewHolderType1 viewHolderType1 = (ExamAdapter.ViewHolderType1) viewHolder;
                viewHolderType1.bindData(mBasicTests.get(i), mContext, isChecked, EXTENSION_PNG);
                break;
            case VIEW_1:
                ExamAdapter.ViewHolderType2 viewHolderType2 = (ExamAdapter.ViewHolderType2) viewHolder;
                viewHolderType2.bindData(mBasicTests.get(i), isChecked);
                break;
            case VIEW_2:
                ExamAdapter.ViewHolderType3 viewHolderType3 = (ExamAdapter.ViewHolderType3) viewHolder;
                viewHolderType3.bindData(mBasicTests.get(i), isChecked);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mBasicTests.size();
    }

    public static class ViewHolderType1 extends ExamAdapter.ViewHolderType1 {

        public ViewHolderType1(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void showAnswer(Exam exam) {
            super.showAnswer(exam);
        }
    }

    public static class ViewHolderType2 extends ExamAdapter.ViewHolderType2 {

        public ViewHolderType2(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ViewHolderType3 extends ExamAdapter.ViewHolderType3 {

        public ViewHolderType3(@NonNull View itemView) {
            super(itemView);
        }
    }
}
