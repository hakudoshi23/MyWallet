package com.haku.wallet.tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Tag;

public class TagListAdapter extends ArrayAdapter<Tag> {
    private final Context context;

    public TagListAdapter(Context context, Tag[] objects) {
        super(context, R.layout.tag_list_item, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tag_list_item, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.tag_list_item_name);
        nameView.setText(this.getItem(position).name);
        TextView colorView = (TextView) rowView.findViewById(R.id.tag_list_item_color);
        colorView.setBackgroundColor(this.getItem(position).color);
        return rowView;
    }
}
