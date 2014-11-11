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
import android.view.*;
import android.widget.*;
import com.avp.wallet.R;
import com.haku.wallet.account.AccountFragment;
import com.haku.wallet.db.Account;
import com.haku.wallet.tag.TagsActivity;

public class DrawerActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener {
    public static AccountsAdapter adapter;
    private ActionBarDrawerToggle toggle;
    private Bundle args = new Bundle();
    private DrawerLayout drawerLayout;

    private Account account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_drawer);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.save, R.string.save
        );
        drawerLayout.setDrawerListener(toggle);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        LinearLayout drawer = (LinearLayout) findViewById(R.id.drawer);
        LinearLayout.inflate(this, R.layout.drawer_menu, drawer);
        ListView accounts = (ListView) findViewById(R.id.drawer_account_list);
        adapter = new AccountsAdapter(this);
        accounts.setAdapter(adapter);
        accounts.setOnItemClickListener(this);

        if (adapter.getCount() > 0) {
            this.setAccount((int) adapter.getItemId(0));
        }
    }

    @Override
    protected void onResume() {
        adapter.update(this);
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
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.activity_account_data, null);
        switch (item.getItemId()) {
            case R.id.action_add:
                builder.setTitle(R.string.account_add);
                builder.setView(layout);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DrawerActivity.this.saveAccount(layout);
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_tags:
                intent = new Intent(this, TagsActivity.class);
                break;
            case R.id.action_delete:
                builder.setTitle(R.string.account_delete_confirmation);
                builder.setPositiveButton(android.R.string.yes, this);
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_edit:
                EditText nameView = (EditText) layout.findViewById(R.id.account_data_name);
                EditText amountView = (EditText) layout.findViewById(R.id.account_data_amount);
                Spinner currencyView = (Spinner) layout.findViewById(R.id.account_data_currency);
                nameView.setText(account.name);
                amountView.setText(String.valueOf(account.amount));
                String[] currencies = this.getResources().getStringArray(R.array.currency);
                for (int i = 0; i < currencies.length; i++) {
                    if (this.account.currency.equals(currencies[i])) currencyView.setSelection(i);
                }
                builder.setTitle(R.string.account_add);
                builder.setView(layout);
                this.account = Account.getAccount(this, this.args.getInt("account"));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DrawerActivity.this.saveAccount(layout);
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                dialog = builder.create();
                dialog.show();
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
            adapter.update(this);
            if (adapter.getCount() > 0) {
                this.setAccount((int) adapter.getItemId(0));
            }
        }
    }

    private void saveAccount(View root) {
        EditText nameView = (EditText) root.findViewById(R.id.account_data_name);
        EditText amountView = (EditText) root.findViewById(R.id.account_data_amount);
        Spinner currencyView = (Spinner) root.findViewById(R.id.account_data_currency);
        if (account == null) account = new Account();
        account.name = nameView.getText().toString();
        if (account.name.equals("")) {
            Toast.makeText(DrawerActivity.this, R.string.warn_empty_name, Toast.LENGTH_LONG).show();
        } else {
            account.currency = currencyView.getSelectedItem().toString();
            String amount = amountView.getText().toString();
            account.amount = Float.parseFloat(amount.equals("") ? "0" : amount);
            if (account.save(DrawerActivity.this)) {
                DrawerActivity.adapter.update(DrawerActivity.this);
            } else {
                Toast.makeText(DrawerActivity.this, "Error: insert", Toast.LENGTH_LONG).show();
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
