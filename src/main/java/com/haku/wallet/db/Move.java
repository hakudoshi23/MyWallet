package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;

public class Move {
    public final int _id;
    public String name;
    public float amount;

    public Move(Cursor cursor) {
        this._id = cursor.getInt(0);
        this.name = cursor.getString(1);
        this.amount = cursor.getFloat(2);
    }

    public static Move[] getByAccount(Context context, int account_id) {
        String query = "select m._id,m.name,m.amount from move m left join account a on m.account_id = a._id where a._id = ?";
        Cursor c = SQLUtil.getDB(context).rawQuery(query, new String[]{String.valueOf(account_id)});
        Move[] moves = new Move[c.getCount()];
        while (c.moveToNext()) moves[c.getPosition()] = new Move(c);
        return moves;
    }
}
