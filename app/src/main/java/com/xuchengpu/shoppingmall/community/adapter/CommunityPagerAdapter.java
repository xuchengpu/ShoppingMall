package com.xuchengpu.shoppingmall.community.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 许成谱 on 2017/3/5 17:31.
 * qq:1550540124
 * for:
 */

//也可以用pageradapter  API一提供了封装好的 FragmentPagerAdapter，使用更方便

public class CommunityPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private String[] titles = new String[]{"新帖", "热帖"};

    public CommunityPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    //返回标题给指示器
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
