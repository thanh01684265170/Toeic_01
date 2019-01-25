package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    protected String mQuestion;
    protected String mResult;
    protected String mAnwserA;
    protected String mAnwserB;
    protected String mAnwserC;
    protected String mAnwserD;

    public Question() {
    }

    protected Question(Parcel in) {
        mQuestion = in.readString();
        mResult = in.readString();
        mAnwserA = in.readString();
        mAnwserB = in.readString();
        mAnwserC = in.readString();
        mAnwserD = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return mQuestion;
    }

    public String getResult() {
        return mResult;
    }

    public String getAnwserA() {
        return mAnwserA;
    }

    public String getAnwserB() {
        return mAnwserB;
    }

    public String getAnwserC() {
        return mAnwserC;
    }

    public String getAnwserD() {
        return mAnwserD;
    }

    public Question(QuestionBuilder questionBuilder) {
        mQuestion = questionBuilder.mQuestion;
        mResult = questionBuilder.mResult;
        mAnwserA = questionBuilder.mAnwserA;
        mAnwserB = questionBuilder.mAnwserB;
        mAnwserC = questionBuilder.mAnwserC;
        mAnwserD = questionBuilder.mAnwserD;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mQuestion);
        parcel.writeString(mResult);
        parcel.writeString(mAnwserA);
        parcel.writeString(mAnwserB);
        parcel.writeString(mAnwserC);
        parcel.writeString(mAnwserD);
    }

    public static class QuestionBuilder {
        private String mQuestion;
        private String mResult;
        private String mAnwserA;
        private String mAnwserB;
        private String mAnwserC;
        private String mAnwserD;

        public QuestionBuilder() {
        }

        public QuestionBuilder setQuestion(String question) {
            mQuestion = question;
            return this;
        }

        public QuestionBuilder setResult(String result) {
            mResult = result;
            return this;
        }

        public QuestionBuilder setAnwserA(String anwserA) {
            mAnwserA = anwserA;
            return this;
        }

        public QuestionBuilder setAnwserB(String anwserB) {
            mAnwserB = anwserB;
            return this;
        }

        public QuestionBuilder setAnwserC(String anwserC) {
            mAnwserC = anwserC;
            return this;
        }

        public QuestionBuilder setAnwserD(String anwserD) {
            mAnwserD = anwserD;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }
}
