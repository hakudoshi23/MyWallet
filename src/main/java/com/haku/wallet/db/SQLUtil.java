package com.haku.wallet.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLUtil extends SQLiteOpenHelper {
    private static SQLUtil instance = null;

    public SQLUtil(Context context) {
        super(context, "my_wallet.db", null, 1);
    }

    public static SQLiteDatabase getDB(Context context) {
        if (SQLUtil.instance == null) {
            SQLUtil.instance = new SQLUtil(context);
        }
        return SQLUtil.instance.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tag.getCreateStatement());
        db.execSQL(Account.getCreateStatement());
        db.execSQL(Moves.getCreateStatement());

        Account.addDefaultData(db);
        Tag.addDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyWallet", String.format("SQLite Upgrade %s -> %s", oldVersion, newVersion));
    }
}
