package com.haku.wallet.account.move;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.avp.wallet.R;
import com.haku.wallet.db.Move;
import com.haku.wallet.db.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountMoveDataActivity extends Activity implements View.OnClickListener {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleCursorAdapter tagsCursor;
    private int account_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_account_move_data);

        this.findViewById(R.id.activity_account_move_data_added).setOnClickListener(this);

        Bundle args = this.getIntent().getExtras();
        if (args != null && args.containsKey("account")) {
            this.account_id = args.getInt("account");
            this.tagsCursor = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, Tag.getTags(this),
                    new String[]{"name"}, new int[]{android.R.id.text1}, 0);
            Spinner tagView = (Spinner) this.findViewById(R.id.activity_account_move_data_tag);
            tagView.setAdapter(this.tagsCursor);
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
            TextView addedView = (TextView) this.findViewById(R.id.activity_account_move_data_added);
            TextView descView = (TextView) this.findViewById(R.id.activity_account_move_data_description);
            Spinner tagView = (Spinner) this.findViewById(R.id.activity_account_move_data_tag);

            Move m = new Move();
            if (nameView.length() == 0) {
                Toast.makeText(this, this.getString(R.string.empty_name), Toast.LENGTH_LONG).show();
            } else {
                try {
                    m.name = nameView.getText().toString();
                    String str_added = addedView.getText().toString();
                    m.added = (str_added.equals("") ? new Date() : sdf.parse(str_added)).getTime();
                    m.description = descView.getText().toString();
                    String str_amount = amountView.getText().toString();
                    m.amount = Float.parseFloat(str_amount.equals("") ? "0" : str_amount);
                    m.tag_id = (int) this.tagsCursor.getItemId(tagView.getSelectedItemPosition());
                    m.account_id = this.account_id;
                    if (m.save(this)) this.finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View v) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (v instanceof TextView) {
                    TextView.class.cast(v).setText(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpDialog.show();
    }
}
