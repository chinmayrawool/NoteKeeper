package com.mad.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chinmay Rawool on 2/27/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static public final String DB_NAME = "mynotes.db";
    static public final int DB_VERSION = 1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        NoteTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        NoteTable.onUpgrade(sqLiteDatabase,i,i1);
    }
}
