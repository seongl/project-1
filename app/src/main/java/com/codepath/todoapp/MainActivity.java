package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TodoItem> todoItems;
    ListView lvItems;
    EditText etEditText;

    TodoItemsAdapter aToDoAdapter;
    TodoItemDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        // Long click deletes an item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TodoItem todoItem = todoItems.get(position);
                aToDoAdapter.notifyDataSetChanged();
                deleteItem(todoItem);
                todoItems.remove(position);
                return true;
            }
        });

        // Short click goes to edit activity
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoEditItemActivity(lvItems.getItemAtPosition(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new TodoItemsAdapter(this, todoItems);
    }

    private void readItems() {
        todoItems = (ArrayList<TodoItem>)databaseHelper.getAllTodoItems();
    }

    private void writeItem(TodoItem newTodoItem) {
        databaseHelper.addTodoItem(newTodoItem);
    }

    private void deleteItem(TodoItem todoItem) {
        databaseHelper.deleteTodoItem(todoItem);
    }

    public void gotoEditItemActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, EditItemActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        startActivity(showOtherActivityIntent);
    }

    public void onAddItem(View view) {
        TodoItem newTodoItem = new TodoItem(etEditText.getText().toString());
        aToDoAdapter.add(newTodoItem);
        etEditText.setText("");
        writeItem(newTodoItem);
    }
}