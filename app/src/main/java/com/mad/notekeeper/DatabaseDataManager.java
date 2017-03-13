package com.mad.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Chinmay Rawool on 2/27/2017.
 */

public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context context){
        mContext =context;
        dbOpenHelper = new DatabaseOpenHelper(mContext);
        db = dbOpenHelper.getWritableDatabase();
        noteDAO = new NoteDAO(db);
    }

    public void close(){
        if(db!=null) {
            db.close();
        }
    }

    public NoteDAO getNoteDAO(){
        return this.noteDAO;
    }

    public long saveNote(Note note){
        return this.noteDAO.save(note);
    }
    public boolean updateNote(Note note){
        return this.noteDAO.update(note);
    }
    public boolean deleteNote(Note note){
        return this.noteDAO.delete(note);
    }

    public Note getNote(int i){
        return this.noteDAO.get(i);
    }

    public List<Note> getAllNote(){
        return this.noteDAO.getAll();
    }


}
