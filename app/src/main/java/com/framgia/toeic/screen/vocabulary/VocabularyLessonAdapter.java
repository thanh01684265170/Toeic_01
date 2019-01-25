package com.framgia.toeic.screen.vocabulary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.VocabularyLessonItem;

import java.util.List;

public class VocabularyLessonAdapter extends RecyclerView.Adapter<VocabularyLessonAdapter.ViewHolder> {
    private Context mContext;
    private List<VocabularyLessonItem> mLessons;

    public VocabularyLessonAdapter(Context context, List<VocabularyLessonItem> vocabularyLessonItems) {
        mContext = context;
        mLessons = vocabularyLessonItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_vocabulary, viewGroup, false);
        return new ViewHolder(mContext, view, mLessons);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mLessons.get(i));
    }

    @Override
    public int getItemCount() {
        return mLessons != null ? mLessons.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle;
        private TextView mTextDescription;
        private CheckBox mCheckBox;
        private Context mContext;
        private List<VocabularyLessonItem> mItemList;

        public ViewHolder(Context context, View itemView, List<VocabularyLessonItem> vocabularyLessonItems) {
            super(itemView);
            mContext = context;
            mItemList = vocabularyLessonItems;
            mTextTitle = itemView.findViewById(R.id.text_title_vocabulary);
            mTextDescription = itemView.findViewById(R.id.text_description_vocabulary);
            mCheckBox = itemView.findViewById(R.id.check_vocabulary);
        }

        public void bindData(final VocabularyLessonItem vocabularyLessonItem) {
            if (vocabularyLessonItem == null) {
                return;
            }
            mTextTitle.setText(mContext.getResources().getString(R.string.title_lesson) + vocabularyLessonItem.getId());
            mTextDescription.setText(vocabularyLessonItem.getName());
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    vocabularyLessonItem.setSelected(b);
                }
            });
            mCheckBox.setChecked(vocabularyLessonItem.isSelected());
        }
    }
}
