package com.haku.wallet.account.move;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;
import com.haku.wallet.db.Tag;

public class AccountMoveDataActivity extends Activity {
    private String[] tag_names;
    private int[] tag_ids;
    private int account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_account_move_data);

        Bundle args = this.getIntent().getExtras();
        if (args != null && args.containsKey("account")) {
            this.account_id = args.getInt("account");
            this.tag_names = Tag.getTagsString(this);
            this.tag_ids = Tag.getTagsIds(this);

            Spinner tagView = (Spinner) this.findViewById(R.id.activity_account_move_data_tag);
            tagView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.tag_names));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            TextView nameView = (TextView) this.findViewById(R.id.activity_account_move_data_name);
            TextView amountView = (TextView) this.findViewById(R.id.activity_account_move_data_amount);
            TextView descView = (TextView) this.findViewById(R.id.activity_account_move_data_description);
            Spinner tagView = (Spinner) this.findViewById(R.id.activity_account_move_data_tag);

            Move m = new Move();
            m.name = nameView.getText().toString();
            m.description = descView.getText().toString();
            m.amount = Float.parseFloat(amountView.getText().toString());
            m.tag_id = this.tag_ids[tagView.getSelectedItemPosition()];
            m.account_id = this.account_id;

            if (m.save(this)) {
                this.finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
