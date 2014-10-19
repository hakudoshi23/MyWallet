package com.haku.wallet.util;

import java.util.ArrayList;

public class StringUtil {
    public static String join(String... strings) {
        StringBuffer sb = new StringBuffer();
        if (strings.length > 0) {
            sb.append(strings[0]);
            for (int i = 1; i < strings.length; i++) {
                sb.append(", ").append(strings[i]);
            }
        }
        return sb.toString();
    }

    public static String join(String[] strings, boolean[] isChecked) {
        ArrayList<String> checked = new ArrayList<String>();
        for (int i = 0; i < strings.length; i++) {
            if (isChecked[i]) checked.add(strings[i]);
        }
        return StringUtil.join(checked.toArray(new String[checked.size()]));
    }
}
