package com.framgia.toeic.screen.user;

import com.framgia.toeic.data.model.Mark;

import java.util.HashMap;
import java.util.List;

public interface UserContract {
    interface View{
        void showError(Exception e);

        void showProgress(List<Integer> values, List<Mark> marks);
    }

    interface Presenter{
        void getMarks();
    }
}
