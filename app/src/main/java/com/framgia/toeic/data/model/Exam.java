package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Exam extends Question implements Parcelable {
    private int mId;
    private boolean mIsSeclected;
    private int mIdImage;

    public Exam() {
    }

    public Exam(ExamBuilder grammarBuilder) {
        super(grammarBuilder);
        mId = grammarBuilder.mId;
        mIsSeclected = grammarBuilder.mIsSeclected;
        mIdImage = grammarBuilder.mIdImage;
    }

    protected Exam(Parcel in) {
        super(in);
        mId = in.readInt();
        mIsSeclected = in.readByte() != 0;
        mIdImage = in.readInt();
    }

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
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
        parcel.writeByte((byte) (mIsSeclected ? 1 : 0));
        parcel.writeInt(mIdImage);
    }

    public static class ExamBuilder extends QuestionBuilder {
        private int mId;
        private boolean mIsSeclected;
        private int mIdImage;

        @Override
        public ExamBuilder setQuestion(String question) {
            super.setQuestion(question);
            return this;
        }

        @Override
        public ExamBuilder setResult(String result) {
            super.setResult(result);
            return this;
        }

        @Override
        public ExamBuilder setAnwserA(String anwserA) {
            super.setAnwserA(anwserA);
            return this;
        }

        @Override
        public ExamBuilder setAnwserB(String anwserB) {
            super.setAnwserB(anwserB);
            return this;
        }

        @Override
        public ExamBuilder setAnwserC(String anwserC) {
            super.setAnwserC(anwserC);
            return this;
        }

        @Override
        public ExamBuilder setAnwserD(String anwserD) {
            super.setAnwserD(anwserD);
            return this;
        }

        public ExamBuilder setId(int id) {
            mId = id;
            return this;
        }

        public ExamBuilder setIsSelected(boolean isSelected) {
            mIsSeclected = isSelected;
            return this;
        }

        public ExamBuilder setidImage(int idImage) {
            mIdImage = idImage;
            return this;
        }

        public int getId() {
            return mId;
        }

        public boolean getIsSeclected() {
            return mIsSeclected;
        }

        public int getIdImage() {
            return mIdImage;
        }

        public Exam build() {
            return new Exam(this);
        }
    }
}
