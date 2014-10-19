package com.haku.wallet.account.filter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Filter;
import com.haku.wallet.db.Tag;
import com.haku.wallet.util.StringUtil;

import java.util.Calendar;

public class AccountFilterFragment extends ListFragment implements View.OnClickListener {
    public boolean[] checked = null;
    public String[] names = null;
    public int[] ids = null;
    public Button tags;
    public Button from;
    public Button to;
    private AccountFilterAdapter adapter;
    private int account_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_filter, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            this.account_id = this.getArguments().getInt("account");
            this.setListAdapter(new AccountFilterAdapter(this.getActivity(),
                    Filter.getByAccount(this.getActivity(), this.account_id)));

            this.names = Tag.getTagsString(this.getActivity());
            this.ids = Tag.getTagsIds(this.getActivity());
            this.checked = new boolean[this.names.length];

            this.tags = (Button) rootView.findViewById(R.id.account_filter_tags);
            this.tags.setOnClickListener(this);
            this.from = (Button) rootView.findViewById(R.id.account_filter_from);
            this.from.setOnClickListener(this);
            this.to = (Button) rootView.findViewById(R.id.account_filter_to);
            this.to.setOnClickListener(this);
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dpDialog;
        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.account_filter_tags:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_activity_tag)
                        .setMultiChoiceItems(this.names, this.checked,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (which >= 0 && which < AccountFilterFragment.this.checked.length) {
                                            AccountFilterFragment.this.checked[which] = isChecked;
                                        }
                                    }
                                })
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tags.setText(StringUtil.join(names, checked));
                            }
                        });
                builder.create().show();
                break;
            case R.id.account_filter_from:
                Toast.makeText(this.getActivity(), "From", Toast.LENGTH_SHORT).show();
                dpDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        from.setText(String.format("%s / %s / %s", dayOfMonth, monthOfYear, year));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                break;
            case R.id.account_filter_to:
                Toast.makeText(this.getActivity(), "To", Toast.LENGTH_SHORT).show();
                dpDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        to.setText(String.format("%s / %s / %s", dayOfMonth, monthOfYear, year));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                break;
        }
    }
}
