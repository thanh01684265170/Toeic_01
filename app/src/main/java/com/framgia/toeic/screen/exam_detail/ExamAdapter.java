package com.framgia.toeic.screen.exam_detail;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.toeic.R;
import com.framgia.toeic.data.model.Exam;

import java.io.File;
import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW = 0;
    private static final int VIEW_1 = 1;
    private static final int VIEW_2 = 2;
    private static final int NUMBER_QUESTION_VIEW = 10;
    private static final int NUMBER_QUESTION_VIEW_1 = 40;
    private static final String QUESTION = "Choose a correct answer";
    private static final String FOLDER_IMAGE = "image";
    private static final String EXTENSION_JPG = ".JPG";
    private Context mContext;
    private List<Exam> mExams;
    private boolean isChecked;

    public ExamAdapter(Context context, List<Exam> exams, boolean isChecked) {
        mContext = context;
        mExams = exams;
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
        if (mExams.get(position).getId() <= NUMBER_QUESTION_VIEW) return VIEW;
        if (mExams.get(position).getId() <= NUMBER_QUESTION_VIEW_1) return VIEW_1;
        return VIEW_2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case VIEW:
                return new ViewHolderType1(
                        LayoutInflater.from(mContext).inflate(R.layout.item_exam_question_type_1,
                                viewGroup, false));
            case VIEW_1:
                return new ViewHolderType2(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_exam_question_type_2, viewGroup, false));
            default:
                return new ViewHolderType3(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_exam_question_type_3, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case VIEW:
                ViewHolderType1 viewHolderType1 = (ViewHolderType1) viewHolder;
                viewHolderType1.bindData(mExams.get(i), mContext, isChecked, EXTENSION_JPG);
                break;
            case VIEW_1:
                ViewHolderType2 viewHolderType2 = (ViewHolderType2) viewHolder;
                viewHolderType2.bindData(mExams.get(i), isChecked);
                break;
            case VIEW_2:
                ViewHolderType3 viewHolderType3 = (ViewHolderType3) viewHolder;
                viewHolderType3.bindData(mExams.get(i), isChecked, mContext, EXTENSION_JPG);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mExams != null ? mExams.size() : 0;
    }

    public static class ViewHolderExam extends RecyclerView.ViewHolder {
        protected TextView mTextViewQuestion;
        protected RadioGroup mRadioGroup;
        protected RadioButton mRadioButtonA;
        protected RadioButton mRadioButtonB;
        protected RadioButton mRadioButtonC;

        public ViewHolderExam(@NonNull android.view.View itemView) {
            super(itemView);
            mTextViewQuestion = itemView.findViewById(R.id.text_question_exam);
            mRadioButtonA = itemView.findViewById(R.id.radio_a);
            mRadioButtonB = itemView.findViewById(R.id.radio_b);
            mRadioButtonC = itemView.findViewById(R.id.radio_c);
            mRadioGroup = itemView.findViewById(R.id.radio_group);
        }

        public void bindData(final Exam exam, boolean checked) {
            if (!exam.getQuestion().equals("")) {
                mTextViewQuestion.setText(exam.getId() + ". " + exam.getQuestion());
            } else {
                mTextViewQuestion.setText(exam.getId() + ". " + QUESTION);
            }
            mRadioButtonA.setText(exam.getAnwserA());
            mRadioButtonB.setText(exam.getAnwserB());
            mRadioButtonC.setText(exam.getAnwserC());
            mRadioGroup.clearCheck();
            mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.radio_a:
                            exam.setCheckAnswerA(true);
                            exam.setCheckAnswerB(false);
                            exam.setCheckAnswerC(false);
                            exam.setCheckAnswerD(false);
                            break;
                        case R.id.radio_b:
                            exam.setCheckAnswerA(false);
                            exam.setCheckAnswerB(true);
                            exam.setCheckAnswerC(false);
                            exam.setCheckAnswerD(false);
                            break;
                        case R.id.radio_c:
                            exam.setCheckAnswerA(false);
                            exam.setCheckAnswerB(false);
                            exam.setCheckAnswerC(true);
                            exam.setCheckAnswerD(false);
                            break;
                        case R.id.radio_d:
                            exam.setCheckAnswerA(false);
                            exam.setCheckAnswerB(false);
                            exam.setCheckAnswerC(false);
                            exam.setCheckAnswerD(true);
                            break;
                    }
                }
            });
            mRadioButtonA.setChecked(exam.isCheckAnswerA());
            mRadioButtonB.setChecked(exam.isCheckAnswerB());
            mRadioButtonC.setChecked(exam.isCheckAnswerC());
            if (checked) {
                showAnswer(exam);
            }
        }

        public void showAnswer(Exam exam) {
            mRadioButtonA.setTextColor(Color.WHITE);
            mRadioButtonB.setTextColor(Color.WHITE);
            mRadioButtonC.setTextColor(Color.WHITE);
            mRadioButtonA.setClickable(false);
            mRadioButtonB.setClickable(false);
            mRadioButtonC.setClickable(false);
            checkAnswer(exam);
            if (exam.getResult().equals(exam.getAnwserA())) {
                mRadioButtonA.setTextColor(Color.BLUE);
                return;
            }

            if (exam.getResult().equals(exam.getAnwserB())) {
                mRadioButtonB.setTextColor(Color.BLUE);
                return;
            }

            if (exam.getResult().equals(exam.getAnwserC())) {
                mRadioButtonC.setTextColor(Color.BLUE);
                return;
            }
        }

        public void checkAnswer(Exam exam) {
            if (!exam.getResult().equals(exam.getAnwserA()) && exam.isCheckAnswerA()) {
                mRadioButtonA.setTextColor(Color.RED);
                return;
            }

            if (!exam.getResult().equals(exam.getAnwserB()) && exam.isCheckAnswerB()) {
                mRadioButtonB.setTextColor(Color.RED);
                return;
            }

            if (!exam.getResult().equals(exam.getAnwserC()) && exam.isCheckAnswerC()) {
                mRadioButtonC.setTextColor(Color.RED);
                return;
            }
        }
    }

    public static class ViewHolderType1 extends ViewHolderExam {
        private ImageView mImageView;
        protected RadioButton mRadioButtonD;

        public ViewHolderType1(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_question);
            mRadioButtonD = itemView.findViewById(R.id.radio_d);
        }

        public void bindData(final Exam exam, final Context context, boolean checked, String extensionImage) {
            super.bindData(exam, checked);
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir(FOLDER_IMAGE, Context.MODE_PRIVATE);
            File mypath = new File(directory, exam.getIdImage() + extensionImage);
            Glide.with(context).load(mypath).into(mImageView);
            mRadioButtonD.setText(exam.getAnwserD());
        }

        @Override
        public void showAnswer(Exam exam) {
            super.showAnswer(exam);
            mRadioButtonD.setTextColor(Color.WHITE);
            if (exam.getResult().equals(exam.getAnwserD()) && !exam.isCheckAnswerD()) {
                mRadioButtonD.setTextColor(Color.BLUE);
                return;
            }
            mRadioButtonD.setClickable(false);
        }

        @Override
        public void checkAnswer(Exam exam) {
            super.checkAnswer(exam);
            if (!exam.getResult().equals(exam.getAnwserD()) && exam.isCheckAnswerC()) {
                mRadioButtonD.setTextColor(Color.RED);
                return;
            }
        }
    }

    public static class ViewHolderType2 extends ViewHolderExam {
        public ViewHolderType2(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class ViewHolderType3 extends ViewHolderExam {
        private RadioButton mRadioButtonD;
        private ImageView mImageViewQuestion;

        public ViewHolderType3(@NonNull View itemView) {
            super(itemView);
            mRadioButtonD = itemView.findViewById(R.id.radio_d);
            mImageViewQuestion = itemView.findViewById(R.id.image_question);
        }

        public void bindData(final Exam exam, boolean checked, Context context, String extensionImage) {
            super.bindData(exam, checked);
            mRadioButtonD.setText(exam.getAnwserD());
            mRadioButtonD.setChecked(exam.isCheckAnswerD());
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir(FOLDER_IMAGE, Context.MODE_PRIVATE);
            File mypath = new File(directory, exam.getIdImage() + extensionImage);
            Glide.with(context).load(mypath).into(mImageViewQuestion);
            mRadioButtonD.setText(exam.getAnwserD());
        }

        @Override
        public void showAnswer(Exam exam) {
            super.showAnswer(exam);
            mRadioButtonD.setTextColor(Color.WHITE);
            if (exam.getResult().equals(exam.getAnwserD()) && !exam.isCheckAnswerD()) {
                mRadioButtonD.setTextColor(Color.BLUE);
                return;
            }
            mRadioButtonD.setClickable(false);
        }

        @Override
        public void checkAnswer(Exam exam) {
            super.checkAnswer(exam);
            if (!exam.getResult().equals(exam.getAnwserD()) && exam.isCheckAnswerC()) {
                mRadioButtonD.setTextColor(Color.RED);
                return;
            }
        }
    }
}
