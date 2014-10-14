package com.haku.wallet.account.cycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Cycle;

public class AccountCyclesAdapter extends ArrayAdapter<Cycle> {
    private final Context context;

    public AccountCyclesAdapter(Context context, Cycle[] objects) {
        super(context, R.layout.tag_list_item, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tag_list_item, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.tag_list_item_name);
        nameView.setText(this.getItem(position).name);
        return rowView;
    }
}
