package com.mad.notekeeper;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Chinmay Rawool on 2/27/2017.
 */

public class NoteTable {
    static final String TABLENAME = "notes";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_PRIORITY= "priority";
    static final String COLUMN_TEXT = "text";
    static final String COLUMN_STATUS = "status";
    static final String COLUMN_UPDATED_TIME = "update_time";

    static public void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLENAME +"(" + COLUMN_ID +" integer primary key autoincrement, "+ COLUMN_TEXT +" text not null, ");
        sb.append(COLUMN_PRIORITY +" text not null, "+COLUMN_UPDATED_TIME +" text not null, "+COLUMN_STATUS +" text not null );");
        try{
            db.execSQL(sb.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP IF EXISTS "+ TABLENAME);
        NoteTable.onCreate(db);
    }
}
