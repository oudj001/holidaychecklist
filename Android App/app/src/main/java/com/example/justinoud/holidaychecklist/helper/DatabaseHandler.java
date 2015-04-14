package com.example.justinoud.holidaychecklist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.justinoud.holidaychecklist.model.Checklist;

import java.util.ArrayList;

/**
 * Created by Justinoud on 04-04-15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // Declare static variables
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "HolidayChecklist";
    private static final String TABLE_CHECKLISTS = "checklist";

    // Declare column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PARTICIPANT = "participant";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHECKLISTS_TABLE = "CREATE TABLE " + TABLE_CHECKLISTS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT," + KEY_IMAGE + " BLOB," + KEY_PARTICIPANT + " TEXT" + ");";
        db.execSQL(CREATE_CHECKLISTS_TABLE);
    }
    // Upgrading database procedure
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLISTS);

        onCreate(db);
    }

    // CREATE Checklist
    public void addChecklist(Checklist checklist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, checklist.getTitle());
        values.put(KEY_PARTICIPANT, checklist.getParticipant().toString());
        values.put(KEY_IMAGE, checklist.getImage()); // Contact Phone
        db.insert(TABLE_CHECKLISTS, null, values);
        db.close();
    }

    //Get checklist based on ID
    public Checklist getChecklist(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHECKLISTS, new String[] {KEY_ID, KEY_TITLE, KEY_PARTICIPANT, KEY_IMAGE}, KEY_ID + "=?", new String[] {String.valueOf(id)}, null,null,null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Checklist checklist = new Checklist(cursor.getInt(cursor.getColumnIndex("id")),
        cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("participant")), cursor.getBlob(cursor.getColumnIndex("image")));
        return checklist;
    }

    //get all checklists existing in DB
    public ArrayList<Checklist> getAllChecklists() {
        ArrayList<Checklist> checklistList = new ArrayList<Checklist>();

        String selectQuery = "SELECT * FROM " + TABLE_CHECKLISTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Checklist checklist = new Checklist();
                checklist.setId(Integer.parseInt(cursor.getString(0)));
                checklist.setTitle(cursor.getString(1));
                checklist.setParticipant(cursor.getString(3));
                checklist.setImage(cursor.getBlob(2));



                checklistList.add(checklist);

            } while (cursor.moveToNext());
        }
        return checklistList;
    }

    public Cursor fetchAllIDs() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(TABLE_CHECKLISTS, new String[] {KEY_ID + " as _id"},
                null, null, null, null, null);
        try{

            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }catch(Exception e)
        {
            return mCursor;
        }

    }

    // get amount of checklists
    public int getChecklistsCount() {
        String countQuery = "SELECT * FROM " + TABLE_CHECKLISTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    // Update Checklist
    public int UpdateChecklist(Checklist checklist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,checklist.getTitle());
        values.put(KEY_PARTICIPANT, checklist.getParticipant().toString());
        values.put(KEY_IMAGE, checklist.getImage());

        return db.update(TABLE_CHECKLISTS, values, KEY_ID + " =?", new String[] {String.valueOf(checklist.getId())});

    }

    public void deleteChecklist(Checklist checklist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHECKLISTS, KEY_ID + " =?", new String[] {String.valueOf(checklist.getId())});
        db.close();
    }



}
