package com.framgia.toeic.screen.user;

import com.framgia.toeic.data.model.Mark;
import com.framgia.toeic.data.repository.MarkRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class UserPresenter implements UserContract.Presenter{
    private MarkRepository mMarkRepository;
    private UserContract.View mView;

    public UserPresenter(MarkRepository repository,
                         UserContract.View view) {
        mMarkRepository = repository;
        mView = view;
    }

    @Override
    public void getMarks() {
        mMarkRepository.getMaxMark(new Callback<List<Integer>>() {
            @Override
            public void onGetDataSuccess(final List<Integer> integers) {
                mMarkRepository.getMarks(new Callback<List<Mark>>() {
                    @Override
                    public void onGetDataSuccess(List<Mark> marks) {
                        mView.showProgress(integers, marks);
                    }

                    @Override
                    public void onGetDataFail(Exception error) {
                        mView.showError(error);
                    }
                });
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }
}
