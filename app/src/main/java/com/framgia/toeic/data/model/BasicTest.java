package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BasicTest extends Exam implements Parcelable {
    protected int mPart;
    protected int mIdAudio;

    public BasicTest(int part) {
        mPart = part;
    }

    public BasicTest(Builder builder) {
        super(builder);
        mPart = builder.mPart;
        mIdAudio = builder.mIdAudio;
    }

    public BasicTest(Parcel in, int part, int idAudio) {
        super(in);
        mPart = part;
        mIdAudio = idAudio;
    }

    protected BasicTest(Parcel in) {
        super(in);
        mPart = in.readInt();
        mIdAudio = in.readInt();
    }

    public int getPart() {
        return mPart;
    }

    public void setPart(int part) {
        mPart = part;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mPart);
        dest.writeInt(mIdAudio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BasicTest> CREATOR = new Creator<BasicTest>() {

        @Override
        public BasicTest createFromParcel(Parcel in) {
            return new BasicTest(in);
        }

        @Override
        public BasicTest[] newArray(int size) {
            return new BasicTest[size];
        }
    };

    public static class Builder extends ExamBuilder {
        private int mPart;
        private int mIdAudio;

        @Override
        public Builder setQuestion(String question) {
            super.setQuestion(question);
            return this;
        }

        @Override
        public Builder setResult(String result) {
            super.setResult(result);
            return this;
        }

        @Override
        public Builder setAnwserA(String anwserA) {
            super.setAnwserA(anwserA);
            return this;
        }

        @Override
        public Builder setAnwserB(String anwserB) {
            super.setAnwserB(anwserB);
            return this;
        }

        @Override
        public Builder setAnwserC(String anwserC) {
            super.setAnwserC(anwserC);
            return this;
        }

        @Override
        public Builder setAnwserD(String anwserD) {
            super.setAnwserD(anwserD);
            return this;
        }

        @Override
        public Builder setId(int id) {
            super.setId(id);
            return this;
        }

        @Override
        public Builder setidImage(int idImage) {
            super.setidImage(idImage);
            return this;
        }

        @Override
        public Builder setIsSelected(boolean isSelected) {
            super.setIsSelected(isSelected);
            return this;
        }

        public Builder setIdAudio(int idAudio) {
            mIdAudio = idAudio;
            return this;
        }

        public Builder setPart(int part) {
            mPart = part;
            return this;
        }

        public BasicTest build(){
            return new BasicTest(this);
        }
    }
}
