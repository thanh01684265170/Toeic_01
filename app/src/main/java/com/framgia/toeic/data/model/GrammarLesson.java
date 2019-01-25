package com.framgia.toeic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GrammarLesson extends Lesson implements Parcelable {
    private List<Grammar> mGrammars;

    public GrammarLesson() {
    }

    public GrammarLesson(Parcel in) {
        super(in);
        mGrammars = in.createTypedArrayList(Grammar.CREATOR);
    }

    public static final Creator<GrammarLesson> CREATOR = new Creator<GrammarLesson>() {
        @Override
        public GrammarLesson createFromParcel(Parcel in) {
            return new GrammarLesson(in);
        }

        @Override
        public GrammarLesson[] newArray(int size) {
            return new GrammarLesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(mGrammars);
    }

    public GrammarLesson(int id, String name) {
        super(id, name);
    }

    public List<Grammar> getGrammars() {
        return mGrammars;
    }

    public void setGrammars(List<Grammar> grammars) {
        mGrammars = grammars;
    }
}

