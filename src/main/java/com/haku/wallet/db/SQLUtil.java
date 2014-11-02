package com.haku.wallet.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil extends SQLiteOpenHelper {
    private static final String DB_NAME = "my_wallet.db";
    private static final int DB_VERSION = 1;
    private static SQLUtil instance = null;
    private Context context = null;

    public SQLUtil(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static SQLiteDatabase getDB(Context context) {
        if (SQLUtil.instance == null) {
            SQLUtil.instance = new SQLUtil(context);
        }
        return SQLUtil.instance.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i <= DB_VERSION; i++) {
            AssetManager assets = this.getContext().getAssets();
            try {
                InputStream is = assets.open(String.format("%s.sql", i));
                for (String command : this.getSQLCommands(is)) {
                    Log.d("SQLUtil", "Command: " + command);
                    db.execSQL(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyWallet", String.format("SQLite Upgrade %s -> %s", oldVersion, newVersion));
        for (int i = oldVersion + 1; i < newVersion; i++) {
            AssetManager assets = this.getContext().getAssets();
            try {
                InputStream is = assets.open(String.format("%s.sql", i));
                for (String command : this.getSQLCommands(is)) {
                    db.execSQL(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Context getContext() {
        return this.context;
    }

    public List<String> getSQLCommands(InputStream is) {
        List<String> commands = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        Reader reader = new InputStreamReader(is);
        try {
            int aux = 0;
            while (aux != -1) {
                aux = reader.read();
                while (aux != ';' && aux != -1) {
                    sb.append((char) aux);
                    aux = reader.read();
                }
                String command = sb.toString().trim();
                if (command.length() > 0) commands.add(command);
                sb.setLength(0);
            }
        } catch (Exception ex) {
            Log.e("SQLUtil", "Error reading SQL file!");
        }

        return commands;
    }
}
