package com.framgia.toeic.screen.basic_test;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTestLesson;

import java.util.List;

public class BasicTestAdapter extends RecyclerView.Adapter<BasicTestAdapter.BasicTestViewHolder> {
    private List<BasicTestLesson> mBasicTestLessons;
    private BasicTestAdapter.OnItemClickListener mListener;

    public BasicTestAdapter(List<BasicTestLesson> basicTestLessons, BasicTestAdapter.OnItemClickListener listener) {
        mBasicTestLessons = basicTestLessons;
        mListener = listener;
    }

    @NonNull
    @Override
    public BasicTestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_basic_test, viewGroup, false);
        return new BasicTestViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicTestViewHolder basicTestViewHolder, int i) {
        basicTestViewHolder.bindData(mBasicTestLessons.get(i));
    }

    @Override
    public int getItemCount() {
        return mBasicTestLessons.size();
    }

    static class BasicTestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextTitle;
        private CardView mCardView;
        private BasicTestLesson mBasicTestLesson;
        private BasicTestAdapter.OnItemClickListener mListener;

        public BasicTestViewHolder(View itemView, BasicTestAdapter.OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageView = itemView.findViewById(R.id.image_basic);
            mTextTitle = itemView.findViewById(R.id.text_name_basic);
            mCardView = itemView.findViewById(R.id.cardview_basic);
            mCardView.setOnClickListener(this);
        }

        public void bindData(final BasicTestLesson basicTestLesson) {
            if (basicTestLesson == null) {
                return;
            }
            mBasicTestLesson = basicTestLesson;
            mTextTitle.setText(basicTestLesson.getName());
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            mListener.onClick(mBasicTestLesson);
        }
    }

    public interface OnItemClickListener {
        void onClick(BasicTestLesson basicTestLesson);
    }
}
