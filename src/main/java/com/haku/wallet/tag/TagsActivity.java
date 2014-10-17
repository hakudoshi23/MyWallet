package com.haku.wallet.tag;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import com.avp.wallet.R;
import com.haku.wallet.db.Tag;

public class TagsActivity extends ListActivity {
    private TagListAdapter mTagListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mTagListAdapter = new TagListAdapter(this, Tag.getTags(this));
        this.registerForContextMenu(this.getListView());
        this.setListAdapter(this.mTagListAdapter);
    }

    @Override
    protected void onResume() {
        this.mTagListAdapter.updateAccounts(Tag.getTags(this));
        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.account_action);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == R.id.action_delete) {
            Tag t = this.mTagListAdapter.getItem(info.position);
            t.delete(this);
            this.mTagListAdapter.updateAccounts(Tag.getTags(this));
        } else if (item.getItemId() == R.id.action_edit) {
            Tag a = this.mTagListAdapter.getItem(info.position);
            Intent i = new Intent(this, TagDataActivity.class);
            i.putExtra("tag", a._id);
            this.startActivity(i);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i = new Intent(this, TagDataActivity.class);
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
