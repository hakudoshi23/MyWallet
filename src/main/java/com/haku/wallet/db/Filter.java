package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Filter {
    private static final String SELECT = "select m._id as _id,m.name as name,m.description as description," +
            "m.amount as amount,m.added as added,a.currency as currency,t.color as color " +
            "from move m " +
            "left join account a on m.account_id = a._id " +
            "left join tag t on m.tag_id = t._id";

    public static Cursor find(Context context, int account_id, Date from, Date to, Integer... tags) {
        ArrayList<String> args = new ArrayList<String>();
        String where = " where a._id = ?";
        args.add(String.valueOf(account_id));
        if (from != null) {
            where += " and m.added >= ?";
            args.add(String.valueOf(account_id));
        }
        if (to != null) {
            where += " and m.added <= ?";
            args.add(Long.toString(from.getTime()));
        }
        if (tags != null && tags.length > 0) {
            where += " and t._id in (";
            args.add(tags[0].toString());
            where += "?";
            for (int i = 1; i < tags.length; i++) {
                args.add(tags[i].toString());
                where += ",?";
            }
            where += ")";
        }
        Log.d("Filter", SELECT + where);
        return SQLUtil.getDB(context).rawQuery(SELECT + where, args.toArray(new String[args.size()]));
    }
}
