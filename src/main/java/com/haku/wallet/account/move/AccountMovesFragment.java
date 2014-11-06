package com.haku.wallet.account.move;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.avp.wallet.R;

public class AccountMovesFragment extends ListFragment implements View.OnClickListener {
    public AccountMovesAdapter adapter;
    private int account_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_moves, container, false);
        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            rootView.findViewById(R.id.fragment_account_move_add).setOnClickListener(this);
            this.account_id = this.getArguments().getInt("account");
            this.adapter = new AccountMovesAdapter(this.getActivity(), account_id);
            this.setListAdapter(this.adapter);
        }
        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        adapter.update(this.getActivity());
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if (this.account_id > 0) {
            if (v.getId() == R.id.fragment_account_move_add) {
                Intent intent = new Intent(this.getActivity(), AccountMoveDataActivity.class);
                intent.putExtra("account", this.account_id);
                this.getActivity().startActivity(intent);
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(this.getActivity(), AccountMoveDataActivity.class);
        i.putExtra("account", account_id);
        i.putExtra("move", (int) id);
        this.getActivity().startActivity(i);
    }
}
