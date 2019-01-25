package com.framgia.toeic.screen.splash;

public interface OnWriteData {
    void writeFileError(Exception e);

    void writeFileSuccess(String link);
}
