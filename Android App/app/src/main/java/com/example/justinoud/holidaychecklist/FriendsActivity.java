package com.example.justinoud.holidaychecklist;

import android.app.ListActivity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class FriendsActivity extends ListActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        listView = (ListView)findViewById(android.R.id.list);

        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME};

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";

        final CursorLoader cursorLoader = new CursorLoader(this,
                queryUri,
                projection,
                selection,
                null,
                null);

        Cursor cursor = cursorLoader.loadInBackground();
        String[] from = {ContactsContract.Contacts.DISPLAY_NAME};
        int[] to = {android.R.id.text1};

        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                from,
                to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String getselected = (String)(listView.getItemAtPosition(position));
                System.out.println(getselected);

                Intent intent = new Intent(FriendsActivity.this, FriendsDetailsActivity.class);

                intent.putExtra("id", id);

                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intentAddFriends = new Intent(FriendsActivity.this, AddFriendsActivity.class);
                startActivity(intentAddFriends);
                return true;
            case R.id.menu_friends:
                return true;
            case R.id.menu_checklists:
                Intent intentChecklist = new Intent(FriendsActivity.this, ChecklistActivity.class);
                startActivity(intentChecklist);
                return true;
            case R.id.menu_info:
                Intent intentInfo = new Intent(FriendsActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}



