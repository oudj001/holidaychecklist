package com.example.justinoud.holidaychecklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class FriendsDetailsActivity extends Activity {

 TextView friend_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getIntent().getIntArrayExtra("id");
        setContentView(R.layout.activity_add_checklist);


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
                Intent intentChecklists = new Intent(FriendsDetailsActivity.this, ChecklistActivity.class);
                startActivity(intentChecklists);
                return true;
            case R.id.menu_friends:
                Intent intentFriends = new Intent(FriendsDetailsActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
                return true;
            case R.id.menu_info:
                Intent intentInfo = new Intent(FriendsDetailsActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
