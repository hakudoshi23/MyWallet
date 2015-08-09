package com.haku.wallet.tag;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.haku.wallet.R;

public class TagSpinnerAdapter extends ArrayAdapter<CharSequence> {
    private final int[] colors;

    public TagSpinnerAdapter(Activity activity) {
        super(activity, android.R.layout.simple_spinner_item);
        this.colors = activity.getResources().getIntArray(R.array.color_values);
        for (int color : this.colors) this.add("");
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View rowView = super.getDropDownView(position, convertView, parent);
        if (rowView != null) rowView.setBackgroundColor(this.colors[position]);
        return rowView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = super.getView(position, convertView, parent);
        if (rowView != null) rowView.setBackgroundColor(this.colors[position]);
        return rowView;
    }
}
