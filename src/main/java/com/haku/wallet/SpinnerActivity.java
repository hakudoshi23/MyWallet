package com.haku.wallet;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.account.AccountDataActivity;
import com.haku.wallet.account.AccountFragment;
import com.haku.wallet.db.Account;
import com.haku.wallet.tag.TagsActivity;

public class SpinnerActivity extends FragmentActivity implements ActionBar.OnNavigationListener, DialogInterface.OnClickListener {
    private final Bundle args = new Bundle();
    private CursorAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_spinner);

        this.spinnerAdapter = new AccountsSpinnerAdapter(this, Account.getAccounts(this));

        ActionBar bar = this.getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setListNavigationCallbacks(spinnerAdapter, this);
        bar.setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_spinner, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(this, AccountDataActivity.class);
                break;
            case R.id.action_tags:
                intent = new Intent(this, TagsActivity.class);
                break;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.delete_confirmation_title);
                builder.setPositiveButton(android.R.string.yes, this);
                builder.setNegativeButton(android.R.string.no, null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        if (intent != null) this.startActivity(intent);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Fragment f = new AccountFragment();
        args.putInt("account", (int) itemId);
        f.setArguments(args);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (this.args.containsKey("account")) {
            Account.delete(this, this.args.getInt("account"));
            this.spinnerAdapter.changeCursor(Account.getAccounts(this));
        } else {
            Toast.makeText(this, "No account selected!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        if (this.spinnerAdapter != null) this.spinnerAdapter.changeCursor(Account.getAccounts(this));
        super.onResume();
    }
}
