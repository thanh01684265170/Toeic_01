package com.framgia.toeic.data.model;

public class Mark {
    private int mId;
    private String mName;
    private int mMark;

    public Mark(int id, String name, int mark) {
        mId = id;
        mName = name;
        mMark = mark;
    }

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

    public int getMark() {
        return mMark;
    }

    public void setMark(int mark) {
        mMark = mark;
    }
}
