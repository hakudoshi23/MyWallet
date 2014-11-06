package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Date;

public class Move {
    public int _id;
    public int account_id;
    public int tag_id;
    public String name;
    public String description;
    public float amount;
    public Long added;

    public Move() {
        this._id = 0;
        this.account_id = 0;
        this.tag_id = 0;
        this.name = "default";
        this.description = null;
        this.amount = 0;
        this.added = null;
    }

    public Move(Cursor c) {
        this._id = c.getInt(c.getColumnIndex("_id"));
        this.account_id = c.getInt(c.getColumnIndex("account_id"));
        this.tag_id = c.getInt(c.getColumnIndex("tag_id"));
        this.name = c.getString(c.getColumnIndex("name"));
        this.description = c.getString(c.getColumnIndex("description"));
        this.amount = c.getFloat(c.getColumnIndex("amount"));
        this.added = c.getLong(c.getColumnIndex("added"));
    }

    public static Cursor getMovesByAccount(Context context, int account_id) {
        String query = "select m._id as _id,m.name as name,m.description as description," +
                "m.amount as amount,m.added as added,a.currency as currency,t.color as color " +
                "from move m " +
                "left join account a on m.account_id = a._id " +
                "left join tag t on m.tag_id = t._id " +
                "where a._id = ? and m.added is not null";
        return SQLUtil.getDB(context).rawQuery(query, new String[]{String.valueOf(account_id)});
    }

    public static Cursor getDebtsByAccount(Context context, int account_id) {
        String query = "select m._id as _id,m.name as name,m.description as description," +
                "m.amount as amount,m.added as added,a.currency as currency,t.color as color " +
                "from move m " +
                "left join account a on m.account_id = a._id " +
                "left join tag t on m.tag_id = t._id " +
                "where a._id = ? and m.added is null";
        return SQLUtil.getDB(context).rawQuery(query, new String[]{String.valueOf(account_id)});
    }

    public static Move getMove(Context context, int id) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from move a where a._id = ?",
                new String[]{String.valueOf(id)});
        return c.moveToNext() ? new Move(c) : null;
    }

    public static void delete(Context context, int id) {
        SQLUtil.getDB(context).delete("move", "_id = ?",
                new String[]{String.valueOf(id)});
    }

    public static void apply(Context context, int id) {
        Move m = Move.getMove(context, id);
        m.added = (new Date()).getTime();
        m.save(context);
    }

    public boolean save(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);
        values.put("added", this.added);
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
