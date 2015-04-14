package com.example.justinoud.holidaychecklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_normal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_checklists:
                Intent intentChecklists = new Intent(InfoActivity.this, ChecklistActivity.class);
                startActivity(intentChecklists);
                return true;
            case R.id.menu_friends:
                Intent intentFriends = new Intent(InfoActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
                return true;
            case R.id.menu_info:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
