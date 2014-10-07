package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Account {
    public final int _id;
    public String name;
    public float amount;

    public Account(String name) {
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

    public static String getCreateStatement() {
        return "create table account (_id integer primary key autoincrement, name text, amount decimal);";
    }

    public static String getDeleteStatement() {
        return "drop table if exists account;";
    }

    public static void addDefaultData(SQLiteDatabase db) {
        db.execSQL("insert into account values(1, 'Pocket', 98.70);");
        db.execSQL("insert into account values(2, 'Bank', 5642.23);");
    }
}
