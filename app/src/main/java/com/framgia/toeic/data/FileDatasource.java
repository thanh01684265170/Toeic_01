package com.framgia.toeic.data;

import com.framgia.toeic.data.source.Callback;

import java.util.List;

public interface FileDatasource {
    interface Local {

    }

    interface Remote {
        void getLinkAudioFiles(Callback<List<String>> callback);

        void getLinkImageFiles(Callback<List<String>> callback);
    }
}
