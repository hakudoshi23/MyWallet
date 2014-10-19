package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

import java.util.Date;

public class Filter {

    public static Filter[] getByAccount(FragmentActivity activity, int account) {
        return new Filter[0];
    }

    public static Cursor find(Context context, int account_id, Date from, Date to, int... tags) {
        String query = "select m._id as _id,m.name as name,m.description as description," +
                "m.amount as amount,a.currency as currency,t.color as color " +
                "from move m " +
                "left join account a on m.account_id = a._id " +
                "left join tag t on m.tag_id = t._id " +
                "where a._id = ?";
        return SQLUtil.getDB(context).rawQuery(query, new String[]{String.valueOf(account_id)});
    }
}
