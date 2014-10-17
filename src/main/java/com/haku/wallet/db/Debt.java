package com.haku.wallet.db;

import android.support.v4.app.FragmentActivity;

public class Debt {
    public int _id;
    public String name;
    public String description;
    public int amount;

    public static Debt[] getByAccount(FragmentActivity activity, int account) {
        return new Debt[0];
    }
}
