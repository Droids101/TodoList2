package com.dennis.todolist;

import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dennis.todolist.Db.Todo;


public class MainActivity extends ActionBarActivity implements NewTodoFragment.onNewTodoCreatedListener,  TodoListFragment.onTodoItemClickedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState!=null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, new TodoListFragment()).commit(); //start by displaying currently created todo's
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /*
        * decide whether the new_todo menu item was clicked;
        * if so, create a new bundle and pass an integer key->value pair ["ACTION"->NewTodoFragment.ACTION_NEW] that represents creation of a new todo
        * set the arguments for the fragment to the bundle above
        * create the new fragment*/
        switch (item.getItemId()){
            case R.id.action_new_todo: {
                NewTodoFragment fragment = new NewTodoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("ACTION", NewTodoFragment.ACTION_NEW); //set the action_type to ACTION_NEW for NewTodoFragment
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*
    * open a fragment that displays the currently created todo's
    */
    @Override
    public void onTodo(long id) {
        //Toast.makeText(this,"new todo created Id: "+id,Toast.LENGTH_LONG).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TodoListFragment()).commit();
    }
    /*
    * method to update a todo
    * create a new bundle and pass an integer key->value pair ["ACTION"->NewTodoFragment.ACTION_UPDATE] that represents update of a todo
        * set the arguments for the fragment to the bundle above and display the fragment
    * */
    @Override
    public void onTodoClicked(Todo item) {
        NewTodoFragment fragment=new NewTodoFragment();
        Bundle b=new Bundle();
        b.putInt("ACTION",NewTodoFragment.ACTION_UPDATE);
        b.putSerializable("todo", item);
        fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
