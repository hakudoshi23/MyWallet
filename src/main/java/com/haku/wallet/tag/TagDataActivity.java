package com.haku.wallet.tag;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.avp.wallet.R;
import com.haku.wallet.db.Tag;

public class TagDataActivity extends ActionBarActivity {
    private Tag tag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_data);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);

        Spinner spinner = (Spinner) this.findViewById(R.id.activity_tag_data_color);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Bundle data = this.getIntent().getExtras();
        if (data != null && data.containsKey("tag")) {
            this.tag = Tag.getTag(this, data.getInt("tag"));
            TextView nameView = (TextView) this.findViewById(R.id.activity_tag_data_name);
            nameView.setText(tag.name);
            spinner.setSelection(this.getColorIndex(this.tag.color));
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
            TextView nameView = (TextView) this.findViewById(R.id.activity_tag_data_name);
            Spinner colorView = (Spinner) this.findViewById(R.id.activity_tag_data_color);
            int[] color_values = this.getResources().getIntArray(R.array.color_value);
            int color = color_values[colorView.getSelectedItemPosition()];
            if (this.tag == null) this.tag = new Tag();
            this.tag.name = nameView.getText().toString();
            this.tag.color = color;
            if (tag.save(this)) {
                this.finish();
            } else {
                Toast.makeText(this, "Error: insert", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getColorIndex(int color) {
        int index = 0;
        int[] colors = this.getResources().getIntArray(R.array.color_value);
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                index = i;
                break;
            }
        }
        return index;
    }
}
