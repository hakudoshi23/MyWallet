package com.haku.wallet.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.DrawerActivity;
import com.haku.wallet.account.cycle.AccountCyclesFragment;
import com.haku.wallet.account.debt.AccountDebtsFragment;
import com.haku.wallet.account.move.AccountMovesFragment;

import java.text.DecimalFormat;

public class AccountFragment extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("0.## €");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        if (DrawerActivity.account != null) {
            TextView nameView = (TextView) rootView.findViewById(R.id.fragment_account_name);
            nameView.setText(DrawerActivity.account.name);
            TextView amountView = (TextView) rootView.findViewById(R.id.fragment_account_amount);
            amountView.setText(df.format(DrawerActivity.account.amount));

            ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.fragment_account_pager);
            FragmentTabHost mTabHost = (FragmentTabHost) rootView.findViewById(R.id.fragment_account_tab_host);
            PagerAdapter mPagerAdapter = new TabHostPager(this.getChildFragmentManager(), mViewPager, mTabHost,
                    Fragment.instantiate(this.getActivity(), AccountMovesFragment.class.getName()),
                    Fragment.instantiate(this.getActivity(), AccountDebtsFragment.class.getName()),
                    Fragment.instantiate(this.getActivity(), AccountCyclesFragment.class.getName()));
            mViewPager.setAdapter(mPagerAdapter);

            mTabHost.setup(this.getActivity(), this.getChildFragmentManager(), R.id.fragment_account_content);
            mTabHost.addTab(mTabHost.newTabSpec("moves").setIndicator("Moves"), AccountMovesFragment.class, this.getArguments());
            mTabHost.addTab(mTabHost.newTabSpec("debts").setIndicator("Debts"), AccountDebtsFragment.class, this.getArguments());
            mTabHost.addTab(mTabHost.newTabSpec("cycles").setIndicator("Cycles"), AccountCyclesFragment.class, this.getArguments());
        }

        return rootView;
    }
}
