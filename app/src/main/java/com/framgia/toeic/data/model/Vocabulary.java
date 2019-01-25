package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vocabulary extends Question implements Parcelable {
    private int mId;
    private boolean mSelected;

    public Vocabulary() {
    }

    public Vocabulary(VocabularyBuilder vocabularyBuilder) {
        super(vocabularyBuilder);
        mId = vocabularyBuilder.mId;
        mSelected = vocabularyBuilder.mCheck;
    }

    protected Vocabulary(Parcel in) {
        super(in);
        mId = in.readInt();
        mSelected = in.readByte() != 0;
    }

    public static final Creator<Vocabulary> CREATOR = new Creator<Vocabulary>() {
        @Override
        public Vocabulary createFromParcel(Parcel in) {
            return new Vocabulary(in);
        }

        @Override
        public Vocabulary[] newArray(int size) {
            return new Vocabulary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mId);
        dest.writeByte((byte) (mSelected ? 1 : 0));
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public static class VocabularyBuilder extends QuestionBuilder {
        private int mId;
        private boolean mCheck;

        @Override
        public VocabularyBuilder setQuestion(String question) {
            super.setQuestion(question);
            return this;
        }

        @Override
        public VocabularyBuilder setResult(String result) {
            super.setResult(result);
            return this;
        }

        @Override
        public VocabularyBuilder setAnwserA(String anwserA) {
            super.setAnwserA(anwserA);
            return this;
        }

        @Override
        public VocabularyBuilder setAnwserB(String anwserB) {
            super.setAnwserB(anwserB);
            return this;
        }

        @Override
        public VocabularyBuilder setAnwserC(String anwserC) {
            super.setAnwserC(anwserC);
            return this;
        }

        @Override
        public QuestionBuilder setAnwserD(String anwserD) {
            super.setAnwserD(anwserD);
            return this;
        }

        public VocabularyBuilder setId(int id) {
            mId = id;
            return this;
        }

        public VocabularyBuilder setCheck(boolean check) {
            mCheck = check;
            return this;
        }

        public int getId() {
            return mId;
        }

        public boolean isCheck() {
            return mCheck;
        }

        public Vocabulary build() {
            return new Vocabulary(this);
        }
    }
}
