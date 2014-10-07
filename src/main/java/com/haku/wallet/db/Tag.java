package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

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
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from tag", new String[]{});
        Tag[] tags = new Tag[c.getCount()];
        while (c.moveToNext()) tags[c.getPosition()] = new Tag(c);
        return tags;
    }

    public static String getCreateStatement() {
        return "create table tag (_id integer primary key autoincrement, name text, color integer);";
    }

    public static String getDeleteStatement() {
        return "drop table if exists tag;";
    }

    public static void addDefaultData(SQLiteDatabase db) {
        db.execSQL("insert into tag values(1, 'Party', " + Color.RED + ");");
        db.execSQL("insert into tag values(2, 'Food', " + Color.BLUE + ");");
        db.execSQL("insert into tag values(3, 'Games', " + Color.GREEN + ");");
    }
}
