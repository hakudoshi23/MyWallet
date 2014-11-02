package com.haku.wallet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Tag {
    public int _id;
    public String name;
    public int color;

    public Tag() {
        this._id = 0;
        this.color = -1;
        this.name = "default";
    }

    public Tag(String name, int color) {
        this._id = 0;
        this.color = color;
        this.name = name;
    }

    public Tag(Cursor c) {
        this._id = c.getInt(0);
        this.color = c.getInt(1);
        this.name = c.getString(2);
    }

    public static Cursor getTags(Context context) {
        return SQLUtil.getDB(context).rawQuery("select _id,name,color from tag", new String[]{});
    }

    public static String[] getTagsString(Context context) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select name from tag", new String[]{});
        String[] names = new String[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToNext();
            names[i] = c.getString(0);
        }
        return names;
    }

    public static int[] getTagsIds(Context context) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select _id from tag", new String[]{});
        int[] names = new int[c.getCount()];
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToNext();
            names[i] = c.getInt(0);
        }
        return names;
    }

    public static Tag getTag(Context context, int id) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from tag where _id = ?",
                new String[]{String.valueOf(id)});
        return c.moveToNext() ? new Tag(c) : null;
    }

    public static boolean delete(Context context, int id) {
        int affected = 0;
        if (id > 0) {
            affected = SQLUtil.getDB(context).delete("tag", "_id = ?",
                    new String[]{String.valueOf(id)});
        }
        return affected == 1;
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
}
