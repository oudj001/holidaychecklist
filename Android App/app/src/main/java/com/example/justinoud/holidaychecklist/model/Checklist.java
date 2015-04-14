package com.example.justinoud.holidaychecklist.model;

/**
 * Created by Justinoud on 04-04-15.
 */
public class Checklist {
    int _id;
    String _title;
    CharSequence _participant;
    byte[] _image;

    public Checklist() {

    }
    public Checklist(int id, String title, CharSequence participant, byte[] image) {
        this._id = id;
        this._title = title;
        this._participant = participant;
        this._image = image;
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

    public CharSequence getParticipant() {
        return this._participant;
    }

    public void setParticipant(CharSequence participant) {
        this._participant = participant;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }
}
