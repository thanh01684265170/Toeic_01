package com.framgia.toeic.data.repository;

import com.framgia.toeic.data.FileDatasource;
import com.framgia.toeic.data.source.Callback;

import java.util.List;

public class FileRepository implements FileDatasource.Remote {
    private static FileRepository sFileRepository;
    private FileDatasource.Remote mRemote;

    public static FileRepository getInstance(FileDatasource.Remote remote) {
        if (sFileRepository == null) {
            sFileRepository = new FileRepository(remote);
        }
        return sFileRepository;
    }

    public FileRepository(FileDatasource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public void getLinkAudioFiles(Callback<List<String>> callback) {
        mRemote.getLinkAudioFiles(callback);
    }

    @Override
    public void getLinkImageFiles(Callback<List<String>> callback) {
        mRemote.getLinkImageFiles(callback);
    }
}
