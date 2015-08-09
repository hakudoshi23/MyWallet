package com.haku.wallet.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.haku.wallet.R;
import com.haku.wallet.account.debt.AccountDebtsFragment;
import com.haku.wallet.account.filter.AccountFilterFragment;
import com.haku.wallet.account.move.AccountMovesFragment;

import java.util.List;
import java.util.Vector;

public class AccountPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new Vector<Fragment>();
    private final Context context;

    public AccountPagerAdapter(FragmentManager fm, Context context, Bundle args) {
        super(fm);
        this.context = context;
        this.fragments.add(Fragment.instantiate(context, AccountMovesFragment.class.getName(), args));
        this.fragments.add(Fragment.instantiate(context, AccountDebtsFragment.class.getName(), args));
        this.fragments.add(Fragment.instantiate(context, AccountFilterFragment.class.getName(), args));
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
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.moves);
            case 1:
                return context.getString(R.string.debts);
            case 2:
                return context.getString(R.string.filter);
            default:
                return null;
        }
    }
}
