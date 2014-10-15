package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Tag {
    public int _id;
    public String name;
    public int color;

    public Tag(String name, int color) {
        this._id = 0;
        this.name = name;
        this.color = color;
    }

    public Tag(Cursor c) {
        this._id = c.getInt(0);
        this.name = c.getString(1);
        this.color = c.getInt(2);
    }

    public static Tag[] getTags(Context context) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select _id,name,color from tag", new String[]{});
        Tag[] tags = new Tag[c.getCount()];
        while (c.moveToNext()) tags[c.getPosition()] = new Tag(c);
        return tags;
    }

    public boolean save(Context context) {
        ContentValues values = new ContentValues();
        values.put("name", this.name);
        values.put("color", this.color);
        int count;
        if (this._id > 0) {
            count = SQLUtil.getDB(context).update("tag", values, "_id = ?",
                    new String[]{String.valueOf(this._id)});
        } else {
            long aux = SQLUtil.getDB(context).insert("tag", null, values);
            count = aux > 0 ? 1 : 0;
        }
        return count == 1;
    }

    public boolean delete(Context context) {
        int affected = 0;
        if (this._id > 0) {
            affected = SQLUtil.getDB(context).delete("tag", "_id = ?",
                    new String[]{String.valueOf(this._id)});
        }
        return affected == 1;
    }
}
