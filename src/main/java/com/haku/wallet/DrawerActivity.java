package com.haku.wallet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.avp.wallet.R;
import com.haku.wallet.account.AccountDataActivity;
import com.haku.wallet.account.AccountFragment;
import com.haku.wallet.db.Account;
import com.haku.wallet.settings.SettingsActivity;
import com.haku.wallet.tag.TagsActivity;

public class DrawerActivity extends FragmentActivity implements ListView.OnItemClickListener {
    private ListView mDrawerList;
    private DrawerListAdapter mDrawerListAdapter;
    private RelativeLayout mDrawer;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drawer);

        this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        this.mDrawerList = (ListView) this.findViewById(R.id.drawer_list);
        this.mDrawer = (RelativeLayout) this.findViewById(R.id.drawer);

        this.mDrawerListAdapter = new DrawerListAdapter(this, Account.getAccounts(this));
        this.registerForContextMenu(this.mDrawerList);
        this.mDrawerList.setAdapter(this.mDrawerListAdapter);
        this.mDrawerList.setOnItemClickListener(this);
        this.mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.setDrawerListener(mDrawerToggle);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActionBar().setHomeButtonEnabled(true);

        if (!this.mDrawerList.getAdapter().isEmpty()) this.display(this.mDrawerListAdapter.getItem(0));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.account_action);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.item_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.action_delete) {
            Account a = this.mDrawerListAdapter.getItem(info.position);
            a.delete(this);
            this.mDrawerListAdapter.updateAccounts(Account.getAccounts(this));
        } else if (item.getItemId() == R.id.action_edit) {
            Account a = this.mDrawerListAdapter.getItem(info.position);
            Intent i = new Intent(this, AccountDataActivity.class);
            i.putExtra("account", a._id);
            this.startActivity(i);
            this.mDrawerLayout.closeDrawer(this.mDrawer);
        }
        return true;
    }

    public void openAddAccount(View view) {
        Intent i = new Intent(this, AccountDataActivity.class);
        this.startActivity(i);
        this.mDrawerLayout.closeDrawer(this.mDrawer);
    }

    public void openTags(View view) {
        Intent i = new Intent(this, TagsActivity.class);
        this.startActivity(i);
        this.mDrawerLayout.closeDrawer(this.mDrawer);
    }

    public void openSettings(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        this.startActivity(i);
        this.mDrawerLayout.closeDrawer(this.mDrawer);
    }

    private void display(Account account) {
        Fragment f = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt("account", account._id);
        f.setArguments(args);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
        this.mDrawerLayout.closeDrawer(this.mDrawer);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.display(this.mDrawerListAdapter.getItem(position));
        this.mDrawerLayout.closeDrawer(this.mDrawer);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.mDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mDrawerListAdapter.updateAccounts(Account.getAccounts(this));
        super.onResume();
    }
}
