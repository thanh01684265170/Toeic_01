package com.framgia.toeic.screen.grammar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.GrammarLesson;

import java.util.List;

public class GrammarLessonAdapter extends RecyclerView.Adapter<GrammarLessonAdapter.Viewholder> {
    private Context mContext;
    private List<GrammarLesson> mLessons;
    private OnItemClickListener mListener;
    private int[] data;

    public GrammarLessonAdapter(Context context, List<GrammarLesson> lessons, OnItemClickListener listener, int[] data) {
        mContext = context;
        mLessons = lessons;
        mListener = listener;
        this.data = data;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grammar, viewGroup, false);
        return new Viewholder(view, mListener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
        viewholder.bindData(mLessons.get(i), mContext);
    }

    @Override
    public int getItemCount() {
        return mLessons != null ? mLessons.size() : 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextTitle;
        private CardView mCardView;
        private GrammarLesson mGrammarLesson;
        private OnItemClickListener mListener;
        private int[] mImages;

        public Viewholder(View itemView, OnItemClickListener listener, int[] data) {
            super(itemView);
            mListener = listener;
            mImageView = itemView.findViewById(R.id.image_grammar);
            mTextTitle = itemView.findViewById(R.id.text_name_grammar);
            mCardView = itemView.findViewById(R.id.cardview_grammar);
            mImages = data;
            mCardView.setOnClickListener(this);
        }

        public void bindData(GrammarLesson grammarLesson, Context context) {
            if (grammarLesson == null) {
                return;
            }
            mGrammarLesson = grammarLesson;
            mTextTitle.setText(grammarLesson.getName());
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            mListener.onClick(mGrammarLesson);
        }
    }

    public interface OnItemClickListener {
        void onClick(GrammarLesson grammarLesson);
    }
}
