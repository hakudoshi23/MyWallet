package com.haku.wallet.account.move;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;

public class AccountMovesFragment extends ListFragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, PopupMenu.OnMenuItemClickListener {
    private static final int CURSOR_LOADER = 0x1;
    private AccountMovesAdapter adapter;
    private int account_id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_moves, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            rootView.findViewById(R.id.fragment_account_move_add).setOnClickListener(this);
            this.account_id = this.getArguments().getInt("account");
            this.adapter = new AccountMovesAdapter(this.getActivity(),
                    Move.getByAccount(this.getActivity(), this.account_id));
            this.setListAdapter(this.adapter);

            this.updateList();
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }

    private void updateList() {
        this.getLoaderManager().destroyLoader(CURSOR_LOADER);
        this.getLoaderManager().restartLoader(CURSOR_LOADER, null, this);
    }

    @Override
    public void onResume() {
        this.updateList();
        super.onResume();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (this.account_id > 0) {
            if (v.getId() == R.id.fragment_account_move_add) {
                Intent intent = new Intent(this.getActivity(), AccountMoveDataActivity.class);
                intent.putExtra("account", this.account_id);
                this.getActivity().startActivity(intent);
            } else if (v.getId() == R.id.move_list_item_popup) {
                Toast.makeText(this.getActivity(), "Popup!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AccountMoveCursorLoader(this.getActivity(), this.account_id);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(null);
    }
}
