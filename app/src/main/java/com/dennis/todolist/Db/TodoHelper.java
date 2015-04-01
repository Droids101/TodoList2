package com.dennis.todolist.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DENNIS on 11/03/2015.
 * Helper class used to create and write into and read from the database
 */
public class TodoHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DB_NAME="todo";
    public TodoHelper(Context context) {
        super(context,DB_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Todo.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Todo.upgrade(db,oldVersion,newVersion);
    }
}
