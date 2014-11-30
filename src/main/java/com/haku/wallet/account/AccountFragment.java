package com.haku.wallet.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.haku.wallet.R;
import com.haku.wallet.account.debt.AccountDebtsFragment;
import com.haku.wallet.account.filter.AccountFilterFragment;
import com.haku.wallet.account.move.AccountMovesFragment;

public class AccountFragment extends Fragment {
    private static TabHostPager mPagerAdapter;

    public static void update(int index) {
        Fragment f = mPagerAdapter.getItem(index);
        f.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            FragmentTabHost mTabHost = (FragmentTabHost) rootView.findViewById(R.id.fragment_account_tab_host);
            ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.fragment_account_pager);

            mPagerAdapter = new TabHostPager(this.getChildFragmentManager(), mViewPager, mTabHost,
                    Fragment.instantiate(this.getActivity(), AccountMovesFragment.class.getName(), this.getArguments()),
                    Fragment.instantiate(this.getActivity(), AccountDebtsFragment.class.getName(), this.getArguments()),
                    Fragment.instantiate(this.getActivity(), AccountFilterFragment.class.getName(), this.getArguments()));
            mViewPager.setAdapter(mPagerAdapter);

            mTabHost.setup(this.getActivity(), this.getChildFragmentManager(), R.id.fragment_account_content);
            mTabHost.addTab(mTabHost.newTabSpec("moves").setIndicator(this.getActivity().getText(R.string.moves)),
                    AccountMovesFragment.class, this.getArguments());
            mTabHost.addTab(mTabHost.newTabSpec("debts").setIndicator(this.getActivity().getText(R.string.debts)),
                    AccountDebtsFragment.class, this.getArguments());
            mTabHost.addTab(mTabHost.newTabSpec("filter").setIndicator(this.getActivity().getText(R.string.filter)),
                    AccountFilterFragment.class, this.getArguments());
        }

        return rootView;
    }
}
