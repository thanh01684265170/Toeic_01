package com.framgia.toeic.data.model;

import android.os.Parcel;

import java.util.List;

public class VocabularyLesson extends Lesson {
    private List<Vocabulary> mVocabularies;

    public VocabularyLesson() {
    }


    public VocabularyLesson(int id, String name) {
        super(id, name);
    }

    public VocabularyLesson(Parcel in) {
        super(in);
    }

    public List<Vocabulary> getVocabularies() {
        return mVocabularies;
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        mVocabularies = vocabularies;
    }
}
