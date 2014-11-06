package com.haku.wallet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Account;
import com.haku.wallet.util.FormatUtil;

public class AccountsSpinnerAdapter extends CursorAdapter {

    public AccountsSpinnerAdapter(Context context) {
        super(context, Account.getAccounts(context), true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.drawer_item_account, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.spinner_account_item_name);
        TextView amountView = (TextView) view.findViewById(R.id.spinner_account_item_amount);
        nameView.setText(cursor.getString(cursor.getColumnIndex("name")));
        String amount = FormatUtil.format(cursor.getFloat(cursor.getColumnIndex("amount")),
                cursor.getString(cursor.getColumnIndex("currency")));
        amountView.setText(amount);
    }

    public void update(Context context) {
        this.swapCursor(Account.getAccounts(context));
    }
}
