package com.haku.wallet.tag;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.avp.wallet.R;

public class TagListAdapter extends CursorAdapter {

    public TagListAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
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
    }
}
