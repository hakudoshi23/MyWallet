package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Account {
    public final int _id;
    public String name;
    public float amount;

    public Account(String name, String amount) {
        this._id = 0;
        this.name = name;
        this.amount = 0;
    }

    public Account(Cursor c) {
        this._id = c.getInt(0);
        this.name = c.getString(1);
        this.amount = c.getFloat(2);
    }

    public static Account[] getAccounts(Context context) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from account", new String[]{});
        Account[] accounts = new Account[c.getCount()];
        while (c.moveToNext()) accounts[c.getPosition()] = new Account(c);
        return accounts;
    }

    public static Account getAccount(Context context, int id) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from account a where a._id = " + id, new String[]{});
        return c.moveToNext() ? new Account(c) : null;
    }

    public boolean save(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);
        values.put("amount", this.amount);
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
