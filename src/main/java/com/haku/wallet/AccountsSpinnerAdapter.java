package com.haku.wallet;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.util.FormatUtil;

import java.text.DecimalFormat;

public class AccountsSpinnerAdapter extends CursorAdapter {
    private static final DecimalFormat df = new DecimalFormat("0.## €");

    public AccountsSpinnerAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.spinner_item_account, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.spinner_account_item_name);
        TextView amountView = (TextView) view.findViewById(R.id.spinner_account_item_amount);
        nameView.setText(cursor.getString(cursor.getColumnIndex("name")));
        String amount = FormatUtil.format(context, cursor.getFloat(cursor.getColumnIndex("amount")),
                cursor.getString(cursor.getColumnIndex("currency")));
        amountView.setText(amount);
    }
}
