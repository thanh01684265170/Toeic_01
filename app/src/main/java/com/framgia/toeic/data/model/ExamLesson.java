package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExamLesson extends Lesson implements Parcelable {
    private List<Exam> mExams;

    public ExamLesson() {
    }

    public ExamLesson(Parcel in) {
        super(in);
        mExams = in.createTypedArrayList(Exam.CREATOR);
    }

    public static final Creator<ExamLesson> CREATOR = new Creator<ExamLesson>() {
        @Override
        public ExamLesson createFromParcel(Parcel in) {
            return new ExamLesson(in);
        }

        @Override
        public ExamLesson[] newArray(int size) {
            return new ExamLesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(mExams);
    }

    public ExamLesson(int id, String name) {
        super(id, name);
    }

    public List<Exam> getExams() {
        return mExams;
    }

    public void setExams(List<Exam> exams) {
        mExams = exams;
    }
}

