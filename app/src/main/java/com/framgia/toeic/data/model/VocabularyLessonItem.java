package com.framgia.toeic.data.model;

import android.os.Parcel;

public class VocabularyLessonItem extends VocabularyLesson {
    private boolean mIsSelected;

    public VocabularyLessonItem(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public VocabularyLessonItem(int id, String name, boolean isSelected) {
        super(id, name);
        mIsSelected = isSelected;
    }

    public VocabularyLessonItem(Parcel in, boolean isSelected) {
        super(in);
        mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
