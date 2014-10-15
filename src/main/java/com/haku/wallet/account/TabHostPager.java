package com.haku.wallet.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TabHostPager extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private final List<Fragment> fragments = new Vector<Fragment>();
    private final FragmentTabHost tabs;
    private final ViewPager pager;

    public TabHostPager(FragmentManager fm, ViewPager pager, FragmentTabHost tabs, Fragment... fragments) {
        super(fm);
        this.tabs = tabs;
        this.pager = pager;
        this.fragments.addAll(Arrays.asList(fragments));

        this.pager.setOnPageChangeListener(this);
        this.tabs.setOnTabChangedListener(this);
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public void onTabChanged(String tag) {
        this.pager.setCurrentItem(this.tabs.getCurrentTab());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.tabs.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
