package com.example.sandy.budgettracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = null;
    private List<String> tabs = null;

    public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
