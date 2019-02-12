package com.framgia.toeic.screen.basic_test_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.toeic.data.model.BasicTest;
import com.framgia.toeic.screen.basic_test_detail.part_fragment.PartFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<BasicTest> mBasicTests;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, List<BasicTest> basicTests, List<Fragment> fragments) {
        super(fm);
        mBasicTests = basicTests;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        List<BasicTest> basicTests;
        int part = ++i;
        basicTests = getBasicTestsForFragment(mBasicTests, part);
        Fragment fragment = PartFragment.newInstance(basicTests, part);
        mFragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 7;
    }

    public List<BasicTest> getBasicTestsForFragment(List<BasicTest> basicTests, int part){
        List<BasicTest> basicTests1 = new ArrayList<>();
        for (BasicTest basicTest: basicTests){
            if (basicTest.getPart() == part){
                basicTests1.add(basicTest);
            }
        }
        return basicTests1;
    }
}
