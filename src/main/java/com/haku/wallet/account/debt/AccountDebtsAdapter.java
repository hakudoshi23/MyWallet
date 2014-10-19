package com.haku.wallet.account.debt;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.util.FormatUtil;

public class AccountDebtsAdapter extends CursorAdapter {

    public AccountDebtsAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.list_item_move, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.move_list_item_name);
        nameView.setText(cursor.getString(cursor.getColumnIndex("name")));

        TextView colorView = (TextView) view.findViewById(R.id.move_list_item_tag_color);
        colorView.setBackgroundColor(cursor.getInt(cursor.getColumnIndex("color")));

        TextView descView = (TextView) view.findViewById(R.id.account_filter_tags);
        String desc = cursor.getString(cursor.getColumnIndex("description"));
        descView.setText(desc.length() > 25 ? desc.substring(0, 25) + "..." : desc);

        TextView amountView = (TextView) view.findViewById(R.id.move_list_item_amount);
        String amount = FormatUtil.format(context, cursor.getFloat(cursor.getColumnIndex("amount")),
                cursor.getString(cursor.getColumnIndex("currency")));
        amountView.setTextColor(amount.startsWith("+") ? Color.rgb(0, 153, 0) : Color.RED);
        amountView.setText(amount);
    }
}
