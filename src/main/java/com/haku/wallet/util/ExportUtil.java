package com.haku.wallet.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.avp.wallet.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExportUtil {

    public static void export(Context context, Cursor data, Format format) {
        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT, format.formatter.format(data));
        send.setType("text/plain");
        context.startActivity(Intent.createChooser(send, context.getText(R.string.send_to)));
    }

    public enum Format {
        XML(new Formatter() {
            @Override
            public String format(Cursor cursor) {
                StringBuilder sb = new StringBuilder();
                if (cursor != null && cursor.getCount() > 0) {
                    int c_count = cursor.getColumnCount();
                    sb.append("<moves>\n");
                    while (cursor.moveToNext()) {
                        sb.append("\t<move>\n");
                        for (int i = 0; i < c_count; i++) {
                            String name = cursor.getColumnName(i);
                            sb.append(String.format("\t\t<%s>%s</%s>\n", name,
                                    cursor.getString(i), name));
                        }
                        sb.append("\t</move>\n");
                    }
                    sb.append("</moves>\n");
                }
                return sb.toString();
            }
        }),
        JSON(new Formatter() {
            @Override
            public String format(Cursor cursor) {
                JSONArray array = new JSONArray();
                if (cursor != null && cursor.getCount() > 0) {
                    int c_count = cursor.getColumnCount();
                    while (cursor.moveToNext()) {
                        try {
                            JSONObject object = new JSONObject();
                            for (int i = 0; i < c_count; i++) {
                                object.put(cursor.getColumnName(i), getValue(cursor, i));
                            }
                            array.put(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return array.toString();
            }
        }),
        CSV(new Formatter() {
            @Override
            public String format(Cursor cursor) {
                StringBuilder sb = new StringBuilder();
                if (cursor != null && cursor.getCount() > 0) {
                    int c_count = cursor.getColumnCount();
                    for (int i = 0; i < c_count; i++) {
                        sb.append(cursor.getColumnName(i) + ",");
                    }
                    sb.setLength(sb.length() - 1);
                    sb.append("\n");
                    while (cursor.moveToNext()) {
                        for (int i = 0; i < c_count; i++) {
                            sb.append(getValue(cursor, i) + ",");
                        }
                        sb.setLength(sb.length() - 1);
                        sb.append("\n");
                    }
                }
                return sb.toString();
            }
        });

        public final Formatter formatter;

        private Format(Formatter formatter) {
            this.formatter = formatter;
        }

        public static String[] getNames() {
            Format[] formats = Format.values();
            String[] names = new String[formats.length];
            for (int i = 0; i < formats.length; i++) {
                names[i] = formats[i].name();
            }
            return names;
        }

        private static Object getValue(Cursor c, int i) {
            switch (c.getType(i)) {
                case Cursor.FIELD_TYPE_STRING:
                    return "'" + c.getString(i) + "'";
                case Cursor.FIELD_TYPE_FLOAT:
                    return c.getFloat(i);
                case Cursor.FIELD_TYPE_INTEGER:
                    return c.getInt(i);
                default:
                    return null;
            }
        }

        public interface Formatter {
            public String format(Cursor cursor);
        }
    }
}
