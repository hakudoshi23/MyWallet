package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Account {
    public final int _id;
    public String name;
    public String currency;
    public float amount;

    public Account() {
        this._id = 0;
        this.name = "default";
        this.name = "$";
        this.amount = 0f;
    }

    public Account(Cursor c) {
        this._id = c.getInt(0);
        this.name = c.getString(1);
        this.currency = c.getString(2);
        this.amount = c.getFloat(3);
    }

    public static Cursor getAccounts(Context context) {
        return SQLUtil.getDB(context).rawQuery("select * from account", new String[]{});
    }

    public static Account getAccount(Context context, int id) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from account a where a._id = " + id, new String[]{});
        return c.moveToNext() ? new Account(c) : null;
    }

    public static void delete(Context context, int id) {
        SQLUtil.getDB(context).delete("account", "_id = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean save(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);
        values.put("amount", this.amount);
        values.put("currency", this.currency);
        int count;
        if (this._id > 0) {
            count = SQLUtil.getDB(context).update("account", values, "_id = ?",
                    new String[]{String.valueOf(this._id)});
        } else {
            long aux = SQLUtil.getDB(context).insert("account", null, values);
            count = aux > 0 ? 1 : 0;
        }
        return count == 1;
    }
}
