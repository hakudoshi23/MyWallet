package com.haku.wallet.account.move;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.haku.wallet.db.Move;

public class AccountMoveCursorLoader extends CursorLoader {
    private final Context context;
    private final int account_id;

    public AccountMoveCursorLoader(Context context, int account_id) {
        super(context);
        this.context = context;
        this.account_id = account_id;
    }

    @Override
    public Cursor loadInBackground() {
        return Move.getByAccount(this.context, this.account_id);
    }
}
