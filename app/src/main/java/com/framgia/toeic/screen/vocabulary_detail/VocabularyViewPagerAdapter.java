package com.framgia.toeic.screen.vocabulary_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.toeic.data.model.Vocabulary;
import com.framgia.toeic.screen.vocabulary_detail.fragment_vocabulary.VocabularyDetailFragment;

import java.util.List;

public class VocabularyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Vocabulary> mVocabularies;
    private List<Fragment> mFragments;

    public VocabularyViewPagerAdapter(FragmentManager fm, List<Vocabulary> vocabularies, List<Fragment> fragments) {
        super(fm);
        mVocabularies = vocabularies;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment =  VocabularyDetailFragment.newInstance(mVocabularies.get(i), i);
        mFragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return mVocabularies.size();
    }

}
