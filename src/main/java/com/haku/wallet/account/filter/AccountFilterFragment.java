package com.haku.wallet.account.filter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import com.avp.wallet.R;
import com.haku.wallet.db.Tag;
import com.haku.wallet.util.ExportUtil;
import com.haku.wallet.util.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountFilterFragment extends ListFragment implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final int CURSOR_LOADER = 0x1;
    public boolean[] checked = null;
    public String[] names = null;
    public int[] ids = null;
    public Button tags;
    public Button from;
    public Button to;
    private AccountFilterCursorLoader loader;
    private AccountFilterAdapter adapter;
    private int account_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account_filter, container, false);

        if (this.getArguments() != null && this.getArguments().containsKey("account")) {
            this.account_id = this.getArguments().getInt("account");
            this.loader = new AccountFilterCursorLoader(this.getActivity(), this.account_id);
            this.adapter = new AccountFilterAdapter(this.getActivity());
            this.setListAdapter(this.adapter);

            this.names = Tag.getTagsString(this.getActivity());
            this.ids = Tag.getTagsIds(this.getActivity());
            this.checked = new boolean[this.names.length];

            this.tags = (Button) rootView.findViewById(R.id.account_list_item_desc);
            this.tags.setOnClickListener(this);
            this.from = (Button) rootView.findViewById(R.id.account_filter_from);
            this.from.setOnClickListener(this);
            this.to = (Button) rootView.findViewById(R.id.account_filter_to);
            this.to.setOnClickListener(this);
            rootView.findViewById(R.id.account_filter_clear).setOnClickListener(this);
            rootView.findViewById(R.id.account_filter_export).setOnClickListener(this);

            this.getLoaderManager().initLoader(CURSOR_LOADER, null, this);
        }

        this.setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dpDialog;
        AlertDialog.Builder builder;
        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.account_list_item_desc:
                builder = new AlertDialog.Builder(this.getActivity());
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
                                loader.setTags(getSelectedTagsId(ids, checked));
                                requestUpdate();
                            }
                        });
                builder.create().show();
                break;
            case R.id.account_filter_from:
                dpDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        from.setText(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
                        loader.setFrom(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
                        requestUpdate();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                break;
            case R.id.account_filter_to:
                dpDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        to.setText(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
                        loader.setTo(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
                        requestUpdate();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpDialog.show();
                break;
            case R.id.account_filter_clear:
                for (int i = 0; i < this.checked.length; i++) this.checked[i] = false;
                this.tags.setText("");
                this.loader.setTags();
                this.from.setText("");
                this.loader.setFrom(null);
                this.to.setText("");
                this.loader.setTo(null);
                this.requestUpdate();
                break;
            case R.id.account_filter_export:
                builder = new AlertDialog.Builder(this.getActivity());
                builder.setTitle(R.string.select_format)
                        .setItems(ExportUtil.Format.getNames(),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ExportUtil.export(getActivity(), loader.loadInBackground(),
                                                ExportUtil.Format.values()[which]);
                                    }
                                }
                        );
                builder.create().show();
                break;
        }
    }

    private Integer[] getSelectedTagsId(int[] ids, boolean[] isChecked) {
        List<Integer> checkedIds = new ArrayList<Integer>();
        for (int i = 0; i < ids.length; i++) {
            if (isChecked[i]) checkedIds.add(ids[i]);
        }
        return checkedIds.toArray(new Integer[checkedIds.size()]);
    }

    private void requestUpdate() {
        this.getLoaderManager().destroyLoader(CURSOR_LOADER);
        this.getLoaderManager().restartLoader(CURSOR_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case CURSOR_LOADER:
                return this.loader;
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(null);
    }
}
