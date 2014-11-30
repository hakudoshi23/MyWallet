package com.haku.wallet.account.move;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.haku.wallet.R;
import com.haku.wallet.db.Move;
import com.haku.wallet.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountMovesAdapter extends CursorAdapter {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final int account_id;

    public AccountMovesAdapter(Context context, int account_id) {
        super(context, Move.getMovesByAccount(context, account_id), true);
        this.account_id = account_id;
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
        String amount = FormatUtil.format(cursor.getFloat(cursor.getColumnIndex("amount")),
                cursor.getString(cursor.getColumnIndex("currency")));
        amountView.setTextColor(amount.startsWith("+") ? Color.rgb(0, 153, 0) : Color.RED);
        amountView.setText(amount);

        TextView dateView = (TextView) view.findViewById(R.id.move_list_item_date);
        long added = cursor.getLong(cursor.getColumnIndex("added"));
        dateView.setText(sdf.format(new Date(added)));

        this.addPopup(context, view.findViewById(R.id.move_list_item_popup),
                cursor.getInt(cursor.getColumnIndex("_id")));
    }

    public void update(Context context) {
        this.swapCursor(Move.getMovesByAccount(context, account_id));
    }

    private void addPopup(final Context context, final View parent, final int id) {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, parent);
                popup.getMenuInflater().inflate(R.menu.menu_popup_move, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                i = new Intent(context, AccountMoveDataActivity.class);
                                i.putExtra("account", account_id);
                                i.putExtra("move", id);
                                context.startActivity(i);
                                break;
                            case R.id.action_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(R.string.move_delete_confirmation);
                                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Move.delete(context, id);
                                        update(context);
                                    }
                                });
                                builder.setNegativeButton(android.R.string.no, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                break;
                            case R.id.action_clone:
                                i = new Intent(context, AccountMoveDataActivity.class);
                                i.putExtra("account", account_id);
                                i.putExtra("move", id);
                                i.putExtra("clone", true);
                                context.startActivity(i);
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
