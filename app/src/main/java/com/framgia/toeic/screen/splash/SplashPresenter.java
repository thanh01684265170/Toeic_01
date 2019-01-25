package com.framgia.toeic.screen.splash;

import com.framgia.toeic.data.repository.FileRepository;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private FileRepository mFileRepository;


    public SplashPresenter(SplashContract.View view, FileRepository fileRepository) {
        mView = view;
        mFileRepository = fileRepository;
    }

    @Override
    public void getLinkAudioFiles() {
        mFileRepository.getLinkAudioFiles(new Callback<List<String>>() {
            @Override
            public void onGetDataSuccess(List<String> strings) {
                mView.downloadAudioFile(strings);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }

    @Override
    public void getLinkImageFiles() {
        mFileRepository.getLinkImageFiles(new Callback<List<String>>() {
            @Override
            public void onGetDataSuccess(List<String> strings) {
                mView.downloadImageFile(strings);
            }

            @Override
            public void onGetDataFail(Exception error) {

            }
        });
    }
}
