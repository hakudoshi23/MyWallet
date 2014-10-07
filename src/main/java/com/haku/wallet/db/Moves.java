package com.haku.wallet.db;

public class Moves {
    public int _id;
    public String name;
    public int amount;

    public static String getCreateStatement() {
        return "create table move (_id integer primary key autoincrement, name text, amount decimal);";
    }

    public static String getDeleteStatement() {
        return "drop table if exists move;";
    }
}
