package com.haku.wallet.account.move;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;

public class AccountMovesAdapter extends ArrayAdapter<Move> {
    private final Context context;

    public AccountMovesAdapter(Context context, Move[] objects) {
        super(context, R.layout.list_item_tag, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_tag, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.tag_list_item_name);
        nameView.setText(this.getItem(position).name);
        return rowView;
    }
}
