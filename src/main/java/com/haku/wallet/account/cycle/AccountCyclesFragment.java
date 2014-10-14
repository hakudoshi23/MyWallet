package com.haku.wallet.account.cycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;

public class AccountCyclesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_cyclic, container, false);

        ListView list = (ListView) rootView.findViewById(android.R.id.list);
        TextView empty = (TextView) rootView.findViewById(android.R.id.empty);

        list.setEmptyView(empty);

        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.cycles, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Toast.makeText(this.getActivity(), "Cycles", Toast.LENGTH_SHORT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
