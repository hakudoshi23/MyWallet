package com.haku.wallet.account;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Toast;
import com.avp.wallet.R;

public class AccountMovesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_moves, container, false);
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