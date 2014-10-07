package com.haku.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Account;

import java.text.DecimalFormat;

public class DrawerListAdapter extends ArrayAdapter<Account> {
    private static final DecimalFormat df = new DecimalFormat("0.##");
    private final Context context;

    public DrawerListAdapter(Context context, Account[] items) {
        super(context, R.layout.drawer_account_item, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drawer_account_item, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.drawer_account_item_name);
        nameView.setText(this.getItem(position).name);
        TextView amountView = (TextView) rowView.findViewById(R.id.drawer_account_item_amount);
        amountView.setText(df.format(this.getItem(position).amount));
        return rowView;
    }
}
