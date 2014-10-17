package com.haku.wallet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawerListAdapter extends ArrayAdapter<Account> {
    private static final DecimalFormat df = new DecimalFormat("0.##");
    private final Activity activity;

    public DrawerListAdapter(Activity activity, Account... items) {
        super(activity, R.layout.drawer_item_account, new ArrayList<Account>(Arrays.asList(items)));
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drawer_item_account, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.drawer_account_item_name);
        nameView.setText(this.getItem(position).name);
        TextView amountView = (TextView) rowView.findViewById(R.id.drawer_account_item_amount);
        amountView.setText(df.format(this.getItem(position).amount));
        return rowView;
    }

    public void updateAccounts(Account... items) {
        this.clear();
        this.addAll(new ArrayList<Account>(Arrays.asList(items)));
    }
}
