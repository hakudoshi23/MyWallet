package com.haku.wallet.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.haku.wallet.R;

public class AccountFragment extends Fragment {
    private static AccountPagerAdapter mPagerAdapter;

    public static void update(int index) {
        Fragment f = mPagerAdapter.getItem(index);
        f.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.fragment_account_pager);

            mPagerAdapter = new AccountPagerAdapter(this.getChildFragmentManager(), this.getActivity(),
                    this.getArguments());
            mViewPager.setAdapter(mPagerAdapter);
        }

        return rootView;
    }
}
