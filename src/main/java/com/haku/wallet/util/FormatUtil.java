package com.haku.wallet.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DecimalFormat;

public class FormatUtil {

    public static String format(Context context, float amount, String currency) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        DecimalFormat df = new DecimalFormat(String.format("+#.00%s;-#.00%s", currency, currency));
        return df.format(amount);
    }
}
