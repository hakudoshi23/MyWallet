package com.haku.wallet.account.debt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;
import com.haku.wallet.db.Tag;

public class AccountDebtDataActivity extends ActionBarActivity {
    private SimpleCursorAdapter tagsCursor;
    private int account_id;

    private TextView nameView;
    private TextView amountView;
    private TextView descView;
    private Spinner tagView;

    private Move move = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_move_data);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);

        this.findViewById(R.id.activity_account_move_data_added).setVisibility(View.INVISIBLE);

        this.nameView = (TextView) this.findViewById(R.id.activity_account_move_data_name);
        this.amountView = (TextView) this.findViewById(R.id.activity_account_move_data_amount);
        this.descView = (TextView) this.findViewById(R.id.activity_account_move_data_description);
        this.tagView = (Spinner) this.findViewById(R.id.activity_account_move_data_tag);

        Bundle args = this.getIntent().getExtras();
        if (args != null && args.containsKey("account")) {
            this.account_id = args.getInt("account");
            this.tagsCursor = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, Tag.getTags(this),
                    new String[]{"name"}, new int[]{android.R.id.text1}, 0);
            this.tagView.setAdapter(this.tagsCursor);

            if (args.containsKey("move")) {
                int move_id = args.getInt("move");
                this.move = Move.getMove(this, move_id);

                this.nameView.setText(this.move.name);
                this.amountView.setText(String.valueOf(this.move.amount));
                this.descView.setText(this.move.description);
                for (int i = 0; i < this.tagsCursor.getCount(); i++) {
                    if (this.move.tag_id == this.tagsCursor.getItemId(i)) {
                        this.tagView.setSelection(i);
                        break;
                    }
                }

                if (args.containsKey("clone") && args.getBoolean("clone")) {
                    this.move._id = 0;
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (nameView.length() == 0) {
                Toast.makeText(this, this.getString(R.string.empty_name), Toast.LENGTH_LONG).show();
            } else {
                if (this.move == null) this.move = new Move();
                this.move.name = nameView.getText().toString();
                this.move.description = descView.getText().toString();
                String str_amount = amountView.getText().toString();
                this.move.amount = Float.parseFloat(str_amount.equals("") ? "0" : str_amount);
                this.move.tag_id = (int) this.tagsCursor.getItemId(tagView.getSelectedItemPosition());
                this.move.account_id = this.account_id;
                this.move.added = null;
                if (this.move.save(this)) this.finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
