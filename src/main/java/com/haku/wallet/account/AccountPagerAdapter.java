package com.haku.wallet.account;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Vector;

public class AccountPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new Vector<Fragment>();

    public AccountPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments.add(Fragment.instantiate(context, AccountMovesFragment.class.getName()));
        fragments.add(Fragment.instantiate(context, AccountDebtsFragment.class.getName()));
        fragments.add(Fragment.instantiate(context, AccountCyclesFragment.class.getName()));
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
