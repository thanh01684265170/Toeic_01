package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BasicTestLesson extends Lesson implements Parcelable {
    private List<BasicTest> mBasicTests;

    public BasicTestLesson(int id, String name, int rating) {
        super(id, name, rating);
    }

    public BasicTestLesson(Parcel in, List<BasicTest> basicTests) {
        super(in);
        mBasicTests = basicTests;
    }

    protected BasicTestLesson(Parcel in) {
        super(in);
        mBasicTests = in.createTypedArrayList(BasicTest.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(mBasicTests);
    }

    public List<BasicTest> getBasicTests() {
        return mBasicTests;
    }

    public void setBasicTests(List<BasicTest> basicTests) {
        mBasicTests = basicTests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BasicTestLesson> CREATOR = new Creator<BasicTestLesson>() {
        @Override
        public BasicTestLesson createFromParcel(Parcel in) {
            return new BasicTestLesson(in);
        }

        @Override
        public BasicTestLesson[] newArray(int size) {
            return new BasicTestLesson[size];
        }
    };
}
