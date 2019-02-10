package com.framgia.toeic.screen.exam;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.ExamLesson;
import com.framgia.toeic.screen.base.RatingCaculator;

import java.util.List;

public class ExamLessonAdapter extends RecyclerView.Adapter<ExamLessonAdapter.Viewholder> {
    private List<ExamLesson> mLessons;
    private OnItemClickListener mListener;

    public ExamLessonAdapter(List<ExamLesson> lessons, OnItemClickListener listener) {
        mLessons = lessons;
        mListener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_exam, viewGroup, false);
        return new Viewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        viewholder.bindData(mLessons.get(i));
    }

    @Override
    public int getItemCount() {
        return mLessons != null ? mLessons.size() : 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextTitle;
        private CardView mCardView;
        private ExamLesson mExamLesson;
        private RatingBar mRatingBar;
        private OnItemClickListener mListener;

        public Viewholder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageView = itemView.findViewById(R.id.image_exam);
            mTextTitle = itemView.findViewById(R.id.text_name_exam);
            mCardView = itemView.findViewById(R.id.cardview_exam);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mCardView.setOnClickListener(this);
        }

        public void bindData(final ExamLesson examLesson) {
            if (examLesson == null) {
                return;
            }
            mExamLesson = examLesson;
            mTextTitle.setText(examLesson.getName());
            RatingCaculator ratingCaculator = new RatingCaculator();
            mRatingBar.setRating(ratingCaculator.rating(examLesson.getRating(),
                    mExamLesson.getExams().size()));
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            mListener.onClick(mExamLesson);
        }
    }

    public interface OnItemClickListener {
        void onClick(ExamLesson examLesson);
    }
}
