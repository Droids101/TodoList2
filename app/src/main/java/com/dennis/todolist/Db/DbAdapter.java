package com.dennis.todolist.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.Serializable;

/**
 * Created by DENNIS on 11/03/2015.
 * class used to manipulate; create, update, select, delete records from the database
 */
public class DbAdapter implements Serializable {

    private Context mContext;
    private SQLiteDatabase db;
    private TodoHelper helper;

    /**
     * constructor for the DbAdapter class with context set, usually as the parent activity
     * @param ctx the context of the DbAdapter object
     */
    public DbAdapter(Context ctx){
        mContext=ctx;
    }

    /**
    *Insert a new row representing a todoItem into the table
     * ; encapsulate it as a content value so that the Content Resolver can contact the db and insert it as a new record
     * @param newItem todoObject holding values that will be inserted into the table
    * */
    public long insert(Todo newItem){
        ContentValues cv=new ContentValues();
        cv.put(Todo.ROW_TITLE, newItem.getTitle());
        cv.put(Todo.ROW_DESC,newItem.getDescription());
        cv.put(Todo.ROW_TIME, newItem.getTime());
        cv.put(Todo.ROW_VENUE,newItem.getVenue());
        return db.insert(Todo.TABLE_NAME,null,cv);
    }
    /**
     * Open a database connection with the context as the current activity
     * @return a writable database object
    * */
    public DbAdapter open()throws SQLiteException{
        helper=new TodoHelper(mContext);
        db=helper.getWritableDatabase();
        return this;
    }
    /*close the currently open db connection that was opened by the activity*/
    public void close(){
        if (helper!=null)
            helper.close();
    }
    /**
     * get all TodoItems currently saved in the database
     */
    public Cursor getAllTodo(){
      return db.query(Todo.TABLE_NAME,Todo.COLS,null,null,null,null,null);
    }

    public Cursor getTodo(long id){
        return db.query(Todo.TABLE_NAME, Todo.COLS,Todo.ROW_ID+"="+id,null,null,null,null);
    }

    /**
    * method to update values of a record.
    * it parcels the TodoObject as content values with [key=>value] pairs of [column_title=>column_value].
    * it returns true if 1 or more rows are affected, false otherwise
     *  @param id the id of the record to be updated
     *  @param newItem todoObject holding up-to-date values that will be updated in the record
    */
    public Boolean update(long id, Todo newItem){
        ContentValues cv=new ContentValues();
        cv.put(Todo.ROW_TITLE, newItem.getTitle());
        cv.put(Todo.ROW_DESC,newItem.getDescription());
        cv.put(Todo.ROW_TIME, newItem.getTime());
        cv.put(Todo.ROW_VENUE,newItem.getVenue());
        return db.update(Todo.TABLE_NAME,cv,Todo.ROW_ID+"="+id, null)>0;
    }

    /**
     * delete a record from the table with id @id
     * @param id the id of the record to be updated
     * @return true
     */
    public Boolean delete(long id){
        return db.delete(Todo.TABLE_NAME,Todo.ROW_ID+"="+id, null)>0;
    }

    /**
     * create a todoObject from the cursor object
     * @param cursor the cursor object used to read and write to the database
    */
    public static Todo fromCursor(Cursor cursor){
        long id=cursor.getColumnIndexOrThrow(Todo.ROW_ID);
        String title=cursor.getString(cursor.getColumnIndexOrThrow(Todo.ROW_TITLE));
        String desc=cursor.getString(cursor.getColumnIndexOrThrow(Todo.ROW_DESC));
        String time=cursor.getString(cursor.getColumnIndexOrThrow(Todo.ROW_TIME));
        String venue=cursor.getString(cursor.getColumnIndexOrThrow(Todo.ROW_VENUE));
        return new Todo(id,title, desc,time,venue);
    }
}
