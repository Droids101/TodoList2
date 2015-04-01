package com.dennis.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dennis.todolist.Db.DbAdapter;
import com.dennis.todolist.Db.Todo;

/**
 * Created by DENNIS on 11/03/2015.
 * fragment to create a new todo
 */
public class NewTodoFragment extends Fragment {
    public EditText txtTitle,txtDesc,txtTime,txtVenue;
    public Button btnCreate;
    public static final int ACTION_UPDATE=2;
    public static final int ACTION_NEW=1;
    private int actionType;
    Todo item=null;

    //String Tag=getArguments().getString("ACTION");


    interface onNewTodoCreatedListener{
        public void onTodo(long id);
    }
    onNewTodoCreatedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_new_todo,container,false);
        txtDesc=(EditText)view.findViewById(R.id.txt_desc);
        txtTime=(EditText)view.findViewById(R.id.txt_time);
        txtTitle=(EditText)view.findViewById(R.id.txt_title);
        txtVenue=(EditText)view.findViewById(R.id.txt_venue);
        btnCreate=(Button)view.findViewById(R.id.btn_create);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener=(onNewTodoCreatedListener)activity;
        }catch (ClassCastException cce){
            throw new ClassCastException("Main activity should implement OnNewTodoCreatedListener");
        }
    }

    private void loadData(){
        item= (Todo) getArguments().getSerializable("todo");
        txtVenue.setText(item.getVenue());
        txtDesc.setText(item.getDescription());
        txtTime.setText(item.getTime());
        txtTitle.setText(item.getTitle());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("test", "\n arguments passed:");
        if(savedInstanceState==null){
            actionType=getArguments().getInt("ACTION");

            if (actionType==ACTION_UPDATE)
                loadData();
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionType == ACTION_NEW ) {
                    /*
                    * create a new a TodoObject and insert it into the database
                     * call onTodo to display the updated list of todo's */
                    DbAdapter dbAdapter = new DbAdapter(getActivity());
                    Todo todo = new Todo(txtTitle.getEditableText().toString(), txtDesc.getEditableText().toString(), txtVenue.getEditableText().toString(), txtTime.getEditableText().toString());
                    dbAdapter.open();
                    long id = dbAdapter.insert(todo);
                    dbAdapter.close();
                    listener.onTodo(id);
                } else {
                    DbAdapter dbAdapter = new DbAdapter(getActivity());
                    Todo todo = new Todo(txtTitle.getEditableText().toString(), txtDesc.getEditableText().toString(), txtVenue.getEditableText().toString(), txtTime.getEditableText().toString());
                    dbAdapter.open();
                    if (dbAdapter.update(item.get_id(), todo)) {
                        dbAdapter.close();
                        listener.onTodo(item.get_id());
                    } else {
                        Toast.makeText(getActivity(), "Update not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
