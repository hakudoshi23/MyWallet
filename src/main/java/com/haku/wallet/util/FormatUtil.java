package com.haku.wallet.util;

import java.text.DecimalFormat;

public class FormatUtil {

    public static String format(float amount, String currency) {
        DecimalFormat df = new DecimalFormat(String.format("+#.00%s;-#.00%s", currency, currency));
        return df.format(amount);
    }
}
