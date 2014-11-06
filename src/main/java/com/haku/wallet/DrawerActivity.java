package com.haku.wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.avp.wallet.R;
import com.haku.wallet.account.AccountDataActivity;
import com.haku.wallet.account.AccountFragment;
import com.haku.wallet.db.Account;
import com.haku.wallet.tag.TagsActivity;

public class DrawerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    private ActionBarDrawerToggle toggle;
    private Bundle args = new Bundle();
    private DrawerLayout drawerLayout;
    private AccountsSpinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drawer);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.add, R.string.add
        );
        drawerLayout.setDrawerListener(toggle);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        LinearLayout drawer = (LinearLayout) findViewById(R.id.drawer);
        drawer.inflate(this, R.layout.drawer_menu, drawer);
        ListView accounts = (ListView) findViewById(R.id.drawer_account_list);
        adapter = new AccountsSpinnerAdapter(this);
        accounts.setAdapter(adapter);
        accounts.setOnItemClickListener(this);

        if (this.adapter.getCount() > 0) {
            this.setAccount((int) this.adapter.getItemId(0));
        }
    }

    @Override
    protected void onResume() {
        this.adapter.update(this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_spinner, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;
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
                builder.setTitle(R.string.account_delete_confirmation_title);
                builder.setPositiveButton(android.R.string.yes, this);
                builder.setNegativeButton(android.R.string.no, null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_edit:
                intent = new Intent(this, AccountDataActivity.class);
                intent.putExtras(this.args);
                break;
        }
        if (intent != null) this.startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.setAccount((int) id);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (this.args.containsKey("account")) {
            Account.delete(this, this.args.getInt("account"));
            this.adapter.update(this);
            if (this.adapter.getCount() > 0) {
                this.setAccount((int) this.adapter.getItemId(0));
            }
        }
    }

    private void setAccount(int id) {
        Fragment f = new AccountFragment();
        this.args.putInt("account", id);
        f.setArguments(args);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
        this.drawerLayout.closeDrawers();
    }
}
