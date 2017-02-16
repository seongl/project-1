package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {

    EditText etUpdateText;
    TodoItem originalTodoItem;
    ArrayList<TodoItem> todoItems;

    TodoItemDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);
        etUpdateText = (EditText)findViewById(R.id.etUpdateText);
        etUpdateText.setText(originalTodoItem.text, TextView.BufferType.EDITABLE);
    }

    public void onSaveItem(View view) {
        updateItems(etUpdateText.getText().toString());
        Intent data = new Intent(EditItemActivity.this, MainActivity.class);
        startActivity(data);
    }

    /**
     * Remove existing value from exisitng location and
     * add updated value to the last position
     *
     * @param newTodoItem
     */
    private void updateItems(String newTodoItem) {
        // Case 1: Exit if same value is entered
        if(newTodoItem != null && newTodoItem.equals(originalTodoItem)) {
            return;
        }
        databaseHelper.updateTodoItem(originalTodoItem, new TodoItem(newTodoItem));
    }
}