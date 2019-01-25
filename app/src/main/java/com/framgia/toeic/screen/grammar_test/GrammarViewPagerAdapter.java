package com.framgia.toeic.screen.grammar_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.toeic.data.model.Grammar;
import com.framgia.toeic.screen.grammar_test.fragment_grammar.GrammarTestFragment;

import java.util.List;

public class GrammarViewPagerAdapter extends FragmentPagerAdapter {
    private List<Grammar> mGrammars;
    private List<Fragment> mFragments;

    public GrammarViewPagerAdapter(FragmentManager fm, List<Grammar> grammars, List<Fragment> fragments) {
        super(fm);
        mGrammars = grammars;
        mFragments = fragments;
    }


    @Override
    public Fragment getItem(int i) {
        Fragment fragment = GrammarTestFragment.newInstance(mGrammars.get(i), i);
        mFragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return mGrammars.size();
    }
}
