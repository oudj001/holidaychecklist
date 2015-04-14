package com.example.justinoud.holidaychecklist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justinoud.holidaychecklist.adapter.ChecklistAdapter;
import com.example.justinoud.holidaychecklist.helper.DatabaseHandler;
import com.example.justinoud.holidaychecklist.model.Checklist;

import java.util.ArrayList;


public class ChecklistActivity extends Activity {

    Button button;
    ImageView imageview;
    ArrayList<Checklist> ChecklistArray = new ArrayList<Checklist>();
    ChecklistAdapter adapter;
    ListView listView;

    TextView noEntryText;
    private int checklist_id;
    private String[] lv_arr = {};
    private static final String TAG = "your activity name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        listView = (ListView) findViewById(R.id.listView);


        DatabaseHandler db = new DatabaseHandler(this);
        int amountChecklist = db.getChecklistsCount();


        if (amountChecklist > 0) {
            ChecklistArray = db.getAllChecklists();

            adapter = new ChecklistAdapter(this, R.layout.list_entry_checklist,
                    ChecklistArray);


            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        }else {
            listView.setVisibility(View.INVISIBLE);
            noEntryText = (TextView) findViewById(R.id.NoEntryText);
            noEntryText.setText(R.string.not_found_checklists);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                DatabaseHandler db = new DatabaseHandler(getBaseContext());
                Cursor cursor = db.fetchAllIDs();
                if(cursor!=null){
                    if(cursor.moveToFirst()){
                        cursor.moveToPosition(position);
                        int contactid = cursor.getInt(cursor.getColumnIndex("_id"));
                        Intent intent = new Intent(ChecklistActivity.this, DetailsChecklistActivity.class);


                        intent.putExtra("id", contactid);

                        startActivity(intent);
                    }
                }

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intentAddChecklist = new Intent(ChecklistActivity.this, DetailsChecklistActivity.class);
                startActivity(intentAddChecklist);
                return true;
            case R.id.menu_checklists:
                return true;
            case R.id.menu_friends:
                Intent intentFriends = new Intent(ChecklistActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
                return true;
            case R.id.menu_info:
                Intent intentInfo = new Intent(ChecklistActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
