package com.example.justinoud.holidaychecklist.model;

/**
 * Created by Justinoud on 04-04-15.
 */
public class Check {
    int _id;
    String _title;
    String _created_at;

    public Check() {

    }
    public Check(int id, String title) {
        this._id = id;
        this._title = title;
    }

    public String getTitle() {
        return this._title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setCreatedAt(String created_at) {
        this._created_at = created_at;
    }


}
