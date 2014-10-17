package com.haku.wallet.account.move;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.*;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;

public class AccountMovesFragment extends ListFragment {
    private AccountMovesAdapter adapter;
    private int account_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_moves, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            this.account_id = this.getArguments().getInt("account");
            this.setListAdapter(new AccountMovesAdapter(this.getActivity(),
                    Move.getByAccount(this.getActivity(), this.account_id)));
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i = new Intent(this.getActivity(), AccountMoveDataActivity.class);
            i.putExtra("account", this.account_id);
            this.getActivity().startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
