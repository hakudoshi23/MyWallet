package com.haku.wallet.db;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;

public class Debt {
    public final int _id;
    public int account_id;
    public int tag_id;
    public String name;
    public String description;
    public float amount;

    public Debt() {
        this._id = 0;
        this.account_id = 0;
        this.tag_id = 0;
        this.name = "default";
        this.name = null;
        this.amount = 0;
    }

    public Debt(Cursor cursor) {
        this._id = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.amount = cursor.getFloat(2);
    }

    public static Cursor getByAccount(FragmentActivity activity, int account_id) {
        String query = "select d._id as _id,d.name as name,d.description as description," +
                "d.amount as amount,a.currency as currency,t.color as color " +
                "from debt d " +
                "left join account a on d.account_id = a._id " +
                "left join tag t on d.tag_id = t._id " +
                "where a._id = ?";
        return SQLUtil.getDB(activity).rawQuery(query, new String[]{String.valueOf(account_id)});
    }
}
