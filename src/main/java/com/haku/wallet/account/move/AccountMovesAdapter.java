package com.haku.wallet.account.move;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountMovesAdapter extends CursorAdapter {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public AccountMovesAdapter(Context context, Cursor cursor) {
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
        dateView.setText(sdf.format(new Date(added * 1000)));

        this.addPopup(context, view.findViewById(R.id.move_list_item_popup),
                cursor.getInt(cursor.getColumnIndex("_id")));
    }

    private void addPopup(final Context context, final View parent, final int id) {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, parent);
                popup.getMenuInflater().inflate(R.menu.menu_popup_move, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Toast.makeText(context, "EDIT " + id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_delete:
                                Toast.makeText(context, "DELETE " + id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_clone:
                                Toast.makeText(context, "CLONE " + id, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }
}
