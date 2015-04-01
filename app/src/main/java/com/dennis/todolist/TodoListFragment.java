package com.dennis.todolist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dennis.todolist.Db.DbAdapter;
import com.dennis.todolist.Db.Todo;

/**
 * Created by DENNIS on 11/03/2015.
 * fragment to view all todos
 */
public class TodoListFragment extends ListFragment {

    private SimpleCursorAdapter adapter;
    private ProgressDialog dialog;
    private DbAdapter db;

    interface onTodoItemClickedListener{
        void onTodoClicked(Todo item);
    }

    onTodoItemClickedListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener= (onTodoItemClickedListener) activity;
        }catch (ClassCastException cce){

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i("TodoList", "this is to do");
        db=new DbAdapter(getActivity());
        db.open();
        adapter=new SimpleCursorAdapter(getActivity(),R.layout.todo_item_template,null, new String[]{Todo.ROW_TITLE}, new int[]{R.id.lbl_title}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        new FetchDataAssync().execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor cursor=(Cursor)adapter.getItem(position);
        Todo item =DbAdapter.fromCursor(cursor);
        Toast.makeText(getActivity(),item.getTitle(),Toast.LENGTH_LONG).show();
        listener.onTodoClicked(item);

    }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    class FetchDataAssync extends AsyncTask<Void,Void,Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            db = new DbAdapter(getActivity());
            db.open();
           return  db.getAllTodo();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= ProgressDialog.show(getActivity(),"Data Activity", "Loading data...." );
        }


        @Override
        protected void onPostExecute(Cursor cursor) {
            dialog.dismiss();
            if (cursor!=null) {
                adapter.swapCursor(cursor);
                adapter.notifyDataSetChanged();
            }

        }
    }
}
