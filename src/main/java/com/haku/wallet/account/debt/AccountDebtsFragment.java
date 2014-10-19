package com.haku.wallet.account.debt;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.avp.wallet.R;
import com.haku.wallet.db.Debt;

public class AccountDebtsFragment extends ListFragment {
    private AccountDebtsAdapter adapter;
    private int account_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_debts, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            this.account_id = this.getArguments().getInt("account");
            this.adapter = new AccountDebtsAdapter(this.getActivity(),
                    Debt.getByAccount(this.getActivity(), this.account_id));
            this.setListAdapter(this.adapter);
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }
}
