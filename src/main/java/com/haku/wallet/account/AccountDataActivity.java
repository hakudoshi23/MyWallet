package com.haku.wallet.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Account;

public class AccountDataActivity extends Activity {
    private Account account = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_account_data);

        Bundle data = this.getIntent().getExtras();
        if (data != null && data.containsKey("account")) {
            this.account = Account.getAccount(this, data.getInt("account"));
            TextView amountView = (TextView) this.findViewById(R.id.account_data_amount);
            TextView nameView = (TextView) this.findViewById(R.id.account_data_name);
            nameView.setText(account.name);
            amountView.setText(String.valueOf(account.amount));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.account_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            TextView amountView = (TextView) this.findViewById(R.id.account_data_amount);
            TextView nameView = (TextView) this.findViewById(R.id.account_data_name);
            if (this.account == null) this.account = new Account();
            this.account.name = nameView.getText().toString();
            this.account.amount = Float.parseFloat(amountView.getText().toString());
            if (account.save(this)) {
                this.finish();
            } else {
                Toast.makeText(this, "Error: insert", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
