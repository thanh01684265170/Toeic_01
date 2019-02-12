package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Exam extends Question implements Parcelable {
    protected int mId;
    protected boolean mIsSeclected;
    protected int mIdImage;
    protected boolean mCheckAnswerA;
    protected boolean mCheckAnswerB;
    protected boolean mCheckAnswerC;
    protected boolean mCheckAnswerD;

    public Exam() {
    }

    public Exam(ExamBuilder grammarBuilder) {
        super(grammarBuilder);
        mId = grammarBuilder.mId;
        mIsSeclected = grammarBuilder.mIsSeclected;
        mIdImage = grammarBuilder.mIdImage;
        mCheckAnswerA = grammarBuilder.mCheckAnswerA;
        mCheckAnswerB = grammarBuilder.mCheckAnswerB;
        mCheckAnswerC = grammarBuilder.mCheckAnswerC;
        mCheckAnswerD = grammarBuilder.mCheckAnswerD;
    }

    protected Exam(Parcel in) {
        super(in);
        mId = in.readInt();
        mIsSeclected = in.readByte() != 0;
        mIdImage = in.readInt();
        mCheckAnswerA = in.readByte() != 0;
        mCheckAnswerB = in.readByte() != 0;
        mCheckAnswerC = in.readByte() != 0;
        mCheckAnswerD = in.readByte() != 0;
    }

    public int getId() {
        return mId;
    }

    public int getIdImage() {
        return mIdImage;
    }

    public boolean isCheckAnswerA() {
        return mCheckAnswerA;
    }

    public void setCheckAnswerA(boolean checkAnswerA) {
        this.mCheckAnswerA = checkAnswerA;
    }

    public boolean isCheckAnswerB() {
        return mCheckAnswerB;
    }

    public void setCheckAnswerB(boolean checkAnswerB) {
        this.mCheckAnswerB = checkAnswerB;
    }

    public boolean isCheckAnswerC() {
        return mCheckAnswerC;
    }

    public void setCheckAnswerC(boolean checkAnswerC) {
        this.mCheckAnswerC = checkAnswerC;
    }

    public boolean isCheckAnswerD() {
        return mCheckAnswerD;
    }

    public void setCheckAnswerD(boolean checkAnswerD) {
        this.mCheckAnswerD = checkAnswerD;
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
        parcel.writeByte((byte) (mCheckAnswerA ? 1 : 0));
        parcel.writeByte((byte) (mCheckAnswerB ? 1 : 0));
        parcel.writeByte((byte) (mCheckAnswerC ? 1 : 0));
        parcel.writeByte((byte) (mCheckAnswerD ? 1 : 0));
    }

    public static class ExamBuilder extends QuestionBuilder {
        private int mId;
        private boolean mIsSeclected;
        private int mIdImage;
        private boolean mCheckAnswerA;
        private boolean mCheckAnswerB;
        private boolean mCheckAnswerC;
        private boolean mCheckAnswerD;

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

        public ExamBuilder setCheckAnswerA(Boolean checkAnswerA) {
            mCheckAnswerA = checkAnswerA;
            return this;
        }

        public ExamBuilder setCheckAnswerB(Boolean checkAnswerB) {
            mCheckAnswerB = checkAnswerB;
            return this;
        }

        public ExamBuilder setCheckAnswerC(Boolean checkAnswerC) {
            mCheckAnswerC = checkAnswerC;
            return this;
        }

        public ExamBuilder setCheckAnswerD(Boolean checkAnswerD) {
            mCheckAnswerD = checkAnswerD;
            return this;
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
