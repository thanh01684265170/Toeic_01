package com.framgia.toeic.data.source.remote;

import android.support.annotation.NonNull;

import com.framgia.toeic.data.FileDatasource;
import com.framgia.toeic.data.source.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FileRemoteDatasource implements FileDatasource.Remote {
    private static final String AUDIOS = "audios";
    private static final String IMAGES = "images";

    @Override
    public void getLinkAudioFiles(final Callback<List<String>> callback) {
        final List<String> links = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(AUDIOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String link = (String) snapshot.getValue();
                    links.add(link);
                }
                callback.onGetDataSuccess(links);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getLinkImageFiles(final Callback<List<String>> callback) {
        final List<String> links = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(IMAGES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String link = (String) snapshot.getValue();
                    links.add(link);
                }
                callback.onGetDataSuccess(links);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
