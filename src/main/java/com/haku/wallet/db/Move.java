package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Move {
    public final int _id;
    public int account_id;
    public int tag_id;
    public String name;
    public String description;
    public float amount;

    public Move() {
        this._id = 0;
        this.account_id = 0;
        this.tag_id = 0;
        this.name = "default";
        this.name = null;
        this.amount = 0;
    }

    public Move(Cursor cursor) {
        this._id = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.amount = cursor.getFloat(2);
    }

    public static Cursor getByAccount(Context context, int account_id) {
        String query = "select m._id as _id,m.name as name,m.description as description," +
                "m.amount as amount,a.currency as currency,t.color as color " +
                "from move m " +
                "left join account a on m.account_id = a._id " +
                "left join tag t on m.tag_id = t._id " +
                "where a._id = ?";
        return SQLUtil.getDB(context).rawQuery(query, new String[]{String.valueOf(account_id)});
    }

    public static void delete(Context context, int id) {
        SQLUtil.getDB(context).delete("move", "_id = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean save(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);
        values.put("amount", this.amount);
        values.put("description", this.description);
        if (this.account_id > 0) values.put("account_id", this.account_id);
        if (this.tag_id > 0) values.put("tag_id", this.tag_id);
        int count;
        if (this._id > 0) {
            count = SQLUtil.getDB(context).update("move", values, "_id = ?",
                    new String[]{String.valueOf(this._id)});
        } else {
            long aux = SQLUtil.getDB(context).insert("move", null, values);
            count = aux > 0 ? 1 : 0;
        }
        return count == 1;
    }
}
