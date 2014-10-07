package com.haku.wallet.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Account;

import java.text.DecimalFormat;

public class AccountFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    private static final DecimalFormat df = new DecimalFormat("0.##");
    public final Account account;

    private PagerAdapter mPagerAdapter;
    private FragmentTabHost mTabHost;
    private ViewPager mViewPager;

    public AccountFragment(Account account) {
        this.account = account;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        TextView nameView = (TextView) rootView.findViewById(R.id.fragment_account_name);
        nameView.setText(account.name);
        TextView amountView = (TextView) rootView.findViewById(R.id.fragment_account_amount);
        amountView.setText(df.format(account.amount));

        this.mPagerAdapter = new AccountPagerAdapter(this.getFragmentManager(), this.getActivity());
        this.mViewPager = (ViewPager) rootView.findViewById(R.id.fragment_account_pager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);

        mTabHost = (FragmentTabHost) rootView.findViewById(R.id.fragment_account_tab_host);
        mTabHost.setup(this.getActivity(), this.getChildFragmentManager(), R.id.fragment_account_content);

        Bundle args = new Bundle();
        mTabHost.addTab(mTabHost.newTabSpec("moves").setIndicator("Moves"), AccountMovesFragment.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("debts").setIndicator("Debts"), AccountDebtsFragment.class, args);
        mTabHost.addTab(mTabHost.newTabSpec("cycles").setIndicator("Cycles"), AccountCyclesFragment.class, args);
        mTabHost.setOnTabChangedListener(this);

        return rootView;
    }

    @Override
    public void onTabChanged(String tag) {
        this.mViewPager.setCurrentItem(this.mTabHost.getCurrentTab());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
