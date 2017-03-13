package com.mad.notekeeper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chinmay Rawool on 2/27/2017.
 */

    public class NoteDAO {
        private SQLiteDatabase db;

        public NoteDAO(SQLiteDatabase db) {
            this.db = db;
        }

        public long save(Note note) {
            ContentValues values = new ContentValues();

            values.put(NoteTable.COLUMN_TEXT,note.getText());
            values.put(NoteTable.COLUMN_PRIORITY,note.getPriority());
            values.put(NoteTable.COLUMN_UPDATED_TIME,note.getUpdate_time());
            values.put(NoteTable.COLUMN_STATUS,note.getStatus());
            return db.insert(NoteTable.TABLENAME,null,values);


        }

        public boolean update(Note note) {
            ContentValues values = new ContentValues();
            values.put(NoteTable.COLUMN_TEXT,note.getText());
            values.put(NoteTable.COLUMN_PRIORITY,note.getPriority());
            values.put(NoteTable.COLUMN_UPDATED_TIME,note.getUpdate_time());
            values.put(NoteTable.COLUMN_STATUS,note.getStatus());
            return db.update(NoteTable.TABLENAME,values,NoteTable.COLUMN_ID+"=?",new String[]{note.getId()+""})>0;
        }

        public boolean delete(Note note) {
            return db.delete(NoteTable.TABLENAME,NoteTable.COLUMN_ID+"=?",new String[]{note.getId()+""})>0;
        }
        public Note get(long id){
            Note note =null;
            Cursor c = db.query(true,NoteTable.TABLENAME,new String[]{NoteTable.COLUMN_ID,NoteTable.COLUMN_TEXT,NoteTable.COLUMN_PRIORITY,NoteTable.COLUMN_UPDATED_TIME,NoteTable.COLUMN_STATUS},NoteTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null);
            if(c!=null && c.moveToFirst()){
                note = buildNoteFromCursor(c);
                if(!c.isClosed()) {
                    c.close();
                }
            }
            return note;
        }
        public List<Note> getAll(){
            List<Note> notes = new ArrayList<Note>();
            Cursor c = db.query(NoteTable.TABLENAME,new String[]{NoteTable.COLUMN_ID,NoteTable.COLUMN_TEXT,NoteTable.COLUMN_PRIORITY,NoteTable.COLUMN_UPDATED_TIME,NoteTable.COLUMN_STATUS},null,null,null,null,null);
            if(c!=null && c.moveToFirst()){
                do {
                    Note note = buildNoteFromCursor(c);
                    if (note != null) {
                        notes.add(note);
                    }
                }while(c.moveToNext());

                if(!c.isClosed()){
                   c.close();
                }
            }
            return notes;

        }

        public Note buildNoteFromCursor(Cursor c){
            Note note = new Note();
            if(c!=null){
                note.setId(c.getLong(0));
                note.setText(c.getString(1));
                note.setPriority(c.getString(2));
                note.setUpdate_time(c.getString(3));
                note.setStatus(c.getString(4));
            }
            return note;
        }
    }
