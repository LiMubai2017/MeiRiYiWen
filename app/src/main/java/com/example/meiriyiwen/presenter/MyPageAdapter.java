package com.example.meiriyiwen.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class MyPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleLists;

    public MyPageAdapter(FragmentManager manager) {
        super(manager);
    }

    public MyPageAdapter(FragmentManager manager, List<Fragment> list, List<String> titleLists) {
        super(manager);
        this.fragmentList = list;
        this.titleLists = titleLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleLists.get(position);
    }
}
