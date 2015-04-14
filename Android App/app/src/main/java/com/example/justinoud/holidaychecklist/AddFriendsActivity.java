package com.example.justinoud.holidaychecklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;


public class AddFriendsActivity extends Activity {

    private PopupWindow pw;
    private boolean expanded; 		//to  store information whether the selected values are displayed completely or in shortened representatn
    public static boolean[] checkSelected;	// store select/unselect information about the values in the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intentAddChecklist = new Intent(AddFriendsActivity.this, ChecklistActivity.class);
                startActivity(intentAddChecklist);
                return true;
            case R.id.menu_friends:
                Intent intentFriends = new Intent(AddFriendsActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
                return true;
            case R.id.menu_info:
                Intent intentInfo = new Intent(AddFriendsActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
