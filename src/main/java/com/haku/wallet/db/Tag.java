package com.haku.wallet.db;

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
        Cursor c = SQLUtil.getDB(context).rawQuery("select t._id,t.name,c.value from tag t left join color c on t.color_id = c._id", new String[]{});
        Tag[] tags = new Tag[c.getCount()];
        while (c.moveToNext()) tags[c.getPosition()] = new Tag(c);
        return tags;
    }
}
