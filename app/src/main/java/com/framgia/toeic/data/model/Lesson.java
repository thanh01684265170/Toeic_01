package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Lesson implements Parcelable {
    private int mId;
    private String mName;
    private int mRating;

    public Lesson() {
    }

    public Lesson(int id, String name) {
        mId = id;
        mName = name;
    }

    public Lesson(int id, String name, int rating) {
        mId = id;
        mName = name;
        mRating = rating;
    }

    protected Lesson(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mRating = in.readInt();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        this.mRating = rating;
    }

    public static Creator<Lesson> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mRating);
    }
}
