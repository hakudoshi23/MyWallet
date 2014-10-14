package com.haku.wallet.account.move;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.DrawerActivity;
import com.haku.wallet.db.Move;

public class AccountMovesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle data) {
        View rootView = inflater.inflate(R.layout.fragment_account_moves, container, false);

        ListView list = (ListView) rootView.findViewById(android.R.id.list);
        TextView empty = (TextView) rootView.findViewById(android.R.id.empty);
        list.setEmptyView(empty);

        if (DrawerActivity.account != null) {
            list.setAdapter(new AccountMovesAdapter(this.getActivity(),
                    Move.getByAccount(this.getActivity(), DrawerActivity.account._id)));
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.moves, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Toast.makeText(this.getActivity(), "Moves", Toast.LENGTH_SHORT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
