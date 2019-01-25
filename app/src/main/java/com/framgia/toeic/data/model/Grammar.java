package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Grammar extends Question implements Parcelable {
    private int mId;
    private boolean mIsSelected;

    public Grammar() {
    }

    public Grammar(GrammarBuilder grammarBuilder) {
        super(grammarBuilder);
        mId = grammarBuilder.mId;
        mIsSelected = grammarBuilder.mIsSelected;
    }

    protected Grammar(Parcel in) {
        super(in);
        mId = in.readInt();
        mIsSelected = in.readByte() != 0;
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public static final Creator<Grammar> CREATOR = new Creator<Grammar>() {
        @Override
        public Grammar createFromParcel(Parcel in) {
            return new Grammar(in);
        }

        @Override
        public Grammar[] newArray(int size) {
            return new Grammar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(mId);
        parcel.writeByte((byte) (mIsSelected ? 1 : 0));
    }

    public static class GrammarBuilder extends QuestionBuilder {
        private int mId;
        private boolean mIsSelected;

        @Override
        public GrammarBuilder setQuestion(String question) {
            super.setQuestion(question);
            return this;
        }

        @Override
        public GrammarBuilder setResult(String result) {
            super.setResult(result);
            return this;
        }

        @Override
        public Grammar.GrammarBuilder setAnwserA(String anwserA) {
            super.setAnwserA(anwserA);
            return this;
        }

        @Override
        public GrammarBuilder setAnwserB(String anwserB) {
            super.setAnwserB(anwserB);
            return this;
        }

        @Override
        public GrammarBuilder setAnwserC(String anwserC) {
            super.setAnwserC(anwserC);
            return this;
        }

        @Override
        public GrammarBuilder setAnwserD(String anwserD) {
            super.setAnwserD(anwserD);
            return this;
        }

        public GrammarBuilder setId(int id) {
            mId = id;
            return this;
        }

        public GrammarBuilder setIsSelected(boolean isSelected) {
            mIsSelected = isSelected;
            return this;
        }

        public int getId() {
            return mId;
        }

        public boolean isSelected() {
            return mIsSelected;
        }

        public Grammar build() {
            return new Grammar(this);
        }
    }
}
