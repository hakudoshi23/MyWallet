package com.haku.wallet.db;

import android.content.Context;
import android.database.Cursor;

public class Color {
    public final int _id;
    public String name;
    public int value;

    public Color(String name, int value) {
        this._id = 0;
        this.name = name;
        this.value = value;
    }

    public Color(Cursor c) {
        this._id = c.getInt(0);
        this.name = c.getString(1);
        this.value = c.getInt(2);
    }

    public static Color[] getColors(Context context) {
        Cursor c = SQLUtil.getDB(context).rawQuery("select * from color", new String[]{});
        Color[] colors = new Color[c.getCount()];
        while (c.moveToNext()) colors[c.getPosition()] = new Color(c);
        return colors;
    }

    public static String[] getColorNames(Context context) {
        Color[] colors = Color.getColors(context);
        String[] names = new String[colors.length];
        for (int i = 0; i < colors.length; i++) names[i] = colors[i].name;
        return names;
    }
}
