package com.haku.wallet.tag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.haku.wallet.R;
import com.haku.wallet.db.Tag;

public class TagListAdapter extends CursorAdapter {

    public TagListAdapter(Context context) {
        super(context, Tag.getTags(context), true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.list_item_tag, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameView = (TextView) view.findViewById(R.id.tag_list_item_name);
        nameView.setText(cursor.getString(cursor.getColumnIndex("name")));
        TextView colorView = (TextView) view.findViewById(R.id.tag_list_item_color);
        colorView.setBackgroundColor(cursor.getInt(cursor.getColumnIndex("color")));
        this.addPopup(context, view.findViewById(R.id.tag_list_item_popup),
                cursor.getInt(cursor.getColumnIndex("_id")));
    }

    public void update(Context context) {
        this.swapCursor(Tag.getTags(context));
    }

    private void addPopup(final Context context, final View parent, final int id) {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, parent);
                popup.getMenuInflater().inflate(R.menu.menu_popup_tag, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent i;
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                i = new Intent(context, TagDataActivity.class);
                                i.putExtra("tag", id);
                                context.startActivity(i);
                                break;
                            case R.id.action_delete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle(R.string.tag_delete_confirmation_title);
                                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Tag.delete(context, id);
                                        update(context);
                                    }
                                });
                                builder.setNegativeButton(android.R.string.no, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
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
