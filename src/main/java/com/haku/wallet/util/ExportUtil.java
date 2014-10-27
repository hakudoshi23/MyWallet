package com.haku.wallet.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.avp.wallet.R;

public class ExportUtil {

    public static void export(Context context, Cursor data, Format format) {
        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT, "name,day,desc\nasd,1,asd");
        send.setType("text/csv");
        context.startActivity(Intent.createChooser(send, context.getText(R.string.send_to)));
    }

    public enum Format {
        XML("text/xml", new Formatter() {
            @Override
            public String format(Cursor cursor) {
                return null;
            }
        }),
        JSON("text/json", new Formatter() {
            @Override
            public String format(Cursor cursor) {
                return null;
            }
        }),
        CSV("text/csv", new Formatter() {
            @Override
            public String format(Cursor cursor) {
                return null;
            }
        });

        public final String mimeType;
        public final Formatter formatter;

        private Format(String mime, Formatter formatter) {
            this.mimeType = mime;
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

        public interface Formatter {
            public String format(Cursor cursor);
        }
    }
}
