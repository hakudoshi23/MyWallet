package com.haku.wallet.account.filter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Filter;
import com.haku.wallet.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFilterAdapter extends CursorAdapter {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public Date from = null, to = null;
    public Integer tags[];

    public AccountFilterAdapter(Context context, int account_id) {
        super(context, Filter.find(context, account_id, null, null, null), false);
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

        TextView descView = (TextView) view.findViewById(R.id.account_list_item_desc);
        String desc = cursor.getString(cursor.getColumnIndex("description"));
        descView.setText(desc.length() > 25 ? desc.substring(0, 25) + "..." : desc);

        TextView amountView = (TextView) view.findViewById(R.id.move_list_item_amount);
        String amount = FormatUtil.format(context, cursor.getFloat(cursor.getColumnIndex("amount")),
                cursor.getString(cursor.getColumnIndex("currency")));
        amountView.setTextColor(amount.startsWith("+") ? Color.rgb(0, 153, 0) : Color.RED);
        amountView.setText(amount);

        TextView dateView = (TextView) view.findViewById(R.id.move_list_item_date);
        long added = cursor.getLong(cursor.getColumnIndex("added"));
        dateView.setText(sdf.format(new Date(added)));

        view.findViewById(R.id.move_list_item_popup).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.move_list_item_popup).getLayoutParams().width = 0;
    }

    public void update(Context context, int account_id) {
        this.swapCursor(Filter.find(context, account_id, from, to, tags));
    }

    public void setFrom(String date) {
        try {
            this.from = date == null ? null : sdf.parse(date);
        } catch (ParseException ex) {
            Log.e("FilterCursorLoader", "Error: Parsing FROM date!");
            ex.printStackTrace(System.err);
        }
    }

    public void setTo(String date) {
        try {
            this.from = date == null ? null : sdf.parse(date);
        } catch (ParseException ex) {
            Log.e("FilterCursorLoader", "Error: Parsing TO date!");
            ex.printStackTrace(System.err);
        }
    }

    public void setTags(Integer... tags) {
        this.tags = tags;
    }
}
