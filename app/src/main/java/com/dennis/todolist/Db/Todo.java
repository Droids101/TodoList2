package com.dennis.todolist.Db;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * Created by DENNIS on 10/03/2015.
 * class represents a relation
 */
public class Todo implements Serializable {
    private long _id;
    private String title;
    private String description;
    private String venue;
    private String time;

    private static final String CREATE_TODO="create table todo("+
            "_id integer primary key autoincrement," +
            "title text not null," +
            "description text," +
            "venue text not null,"+
            "time text not null);";

    public  static final String ROW_ID="_id";
    public  static final String ROW_TITLE="title";
    public static final String ROW_DESC="description";
    public static final String ROW_VENUE="venue";
    public static final String ROW_TIME="time";
    public static final String TABLE_NAME="todo";


    //array used to map a single row to a ToDo object
    public static final String [] COLS={ROW_ID,ROW_TITLE,ROW_DESC,ROW_VENUE,ROW_TIME};

    //create a new db
    public static void create(SQLiteDatabase db){
        db.execSQL(CREATE_TODO);
    }
    

    //support upgrade of application with data persistence
    public static void upgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table todo");
        create(db);
    }

    public Todo(long _id, String title, String description, String venue, String time) {
        this._id=_id;
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.time = time;
    }

    public Todo(String title, String description, String venue, String time) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.time = time;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
