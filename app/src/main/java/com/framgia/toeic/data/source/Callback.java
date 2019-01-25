package com.framgia.toeic.data.source;

public interface Callback<K> {
    void onGetDataSuccess(K k);

    void onGetDataFail(Exception error);
}
