package com.haku.wallet.account.debt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Debt;

public class AccountDebtsAdapter extends ArrayAdapter<Debt> {
    private final Context context;

    public AccountDebtsAdapter(Context context, Debt[] objects) {
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
