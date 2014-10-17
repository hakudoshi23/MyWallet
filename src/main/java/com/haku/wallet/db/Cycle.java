package com.haku.wallet.db;

import android.support.v4.app.FragmentActivity;

public class Cycle {
    public int _id;
    public String name;
    public float amount;

    public static Cycle[] getByAccount(FragmentActivity activity, int account) {
        return new Cycle[0];
    }
}
