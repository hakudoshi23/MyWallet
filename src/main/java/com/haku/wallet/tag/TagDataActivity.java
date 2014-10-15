package com.haku.wallet.tag;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Tag;

public class TagDataActivity extends Activity {
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_data);

        Spinner spinner = (Spinner) this.findViewById(R.id.activity_tag_data_color);
        this.adapter = ArrayAdapter.createFromResource(this,
                R.array.color_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.tag_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            TextView nameView = (TextView) this.findViewById(R.id.activity_tag_data_name);
            Spinner colorView = (Spinner) this.findViewById(R.id.activity_tag_data_color);
            int[] color_values = this.getResources().getIntArray(R.array.color_value);
            int color = color_values[colorView.getSelectedItemPosition()];
            Tag tag = new Tag(nameView.getText().toString(), color);
            if (tag.save(this)) {
                this.finish();
            } else {
                Toast.makeText(this, "Error: insert", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
