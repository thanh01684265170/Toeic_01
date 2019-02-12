package com.framgia.toeic.screen.basic_test_detail.part_fragment;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.screen.base.BaseFragment;
import com.framgia.toeic.screen.base.DisplayAnswerListener;
import com.framgia.toeic.screen.base.MediaPlayerInstance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.toeic.screen.base.ResultTest.FORLDER_MEDIA;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartFragment extends BaseFragment implements DisplayAnswerListener {
    private static final String ARGUMENT_BASIC_TESTS = "ARGUMENT_BASIC_TESTS";
    private static final String ARGUMENT_PART = "ARGUMENT_PART";
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private List<BasicTest> mBasicTests;
    private int mPart;
    private TextView mTextPart;
    private  BasicTestDetailAdapter mAdapter;

    public static Fragment newInstance(List<BasicTest> basicTests, int part){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARGUMENT_BASIC_TESTS, (ArrayList<? extends Parcelable>) basicTests);
        bundle.putInt(ARGUMENT_PART, part);
        Fragment fragment = new PartFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_part;
    }

    @Override
    public void initComponent(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_basic_test);
        mTextPart = view.findViewById(R.id.text_part);
        mSeekBar = view.findViewById(R.id .seekBar);
    }

    @Override
    public void initData() {
        mBasicTests = getArguments().getParcelableArrayList(ARGUMENT_BASIC_TESTS);
        mPart = getArguments().getInt(ARGUMENT_PART);
        mTextPart.setText(mPart+"");
    }

    @Override
    public void showData() {
        mAdapter = new BasicTestDetailAdapter(getContext(), mBasicTests, false, mPart);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showAnswer() {
        mAdapter.setChecked(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void disableClick() {

    }
}
