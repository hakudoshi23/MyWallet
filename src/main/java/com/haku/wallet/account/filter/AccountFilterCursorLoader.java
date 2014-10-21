package com.haku.wallet.account.filter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import com.haku.wallet.db.Filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountFilterCursorLoader extends CursorLoader {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final Context context;
    public Date from = null, to = null;
    public Integer tags[], account_id;

    public AccountFilterCursorLoader(Context context, int account_id) {
        super(context);
        this.context = context;
        this.account_id = account_id;
    }

    @Override
    public Cursor loadInBackground() {
        return Filter.find(this.context, this.account_id, from, to, tags);
    }

    public void setFrom(String date) {
        try {
            this.from = date == null ? null : sdf.parse(date);
        } catch (ParseException ex) {
            Log.e("FilterCursorLoader", "Error: Parsing FROM date!");
            ex.printStackTrace(System.err);
        }
    }

    public void setTo(String date) {
        try {
            this.from = date == null ? null : sdf.parse(date);
        } catch (ParseException ex) {
            Log.e("FilterCursorLoader", "Error: Parsing TO date!");
            ex.printStackTrace(System.err);
        }
    }

    public void setTags(Integer... tags) {
        this.tags = tags;
    }
}
