package com.framgia.toeic.screen.splash;

import java.util.List;

public interface SplashContract {
    interface View {
        void downloadAudioFile(List<String> links);

        void downloadImageFile(List<String> links);
    }

    interface Presenter {

        void getLinkAudioFiles();

        void getLinkImageFiles();
    }
}
