package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;

public class AddActivity extends AppCompatActivity {
    EditText etEditText;
    EditText noteEditText;

    ArrayAdapter priorityAdapter, statusAdapter;
    TodoItemDatabaseHelper databaseHelper;
    Spinner prioritySpinner, statusSpinner;

    TodoItem originalTodoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);

        etEditText = (EditText) findViewById(R.id.etEditText);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);

        priorityAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array, R.layout.activity_add);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.activity_add);

        if(originalTodoItem != null) {
            etEditText.setText(originalTodoItem.text);
            noteEditText.setText(originalTodoItem.notes);
            prioritySpinner.setSelection(priorityAdapter.getPosition(originalTodoItem.priority));
            statusSpinner.setSelection(statusAdapter.getPosition(originalTodoItem.status));
        }
    }

    public void onSaveNewItem(View view) {
        TodoItem newTodoItem = new TodoItem(etEditText.getText().toString(),
                prioritySpinner.getSelectedItem().toString(),
                statusSpinner.getSelectedItem().toString(),
                noteEditText.getText().toString());

        if(newTodoItem.equals(originalTodoItem)) {
            return;
        }

        etEditText.setText("");
        noteEditText.setText("");
        writeItem(newTodoItem);
        gotoMainActivity(newTodoItem);
    }

    private void writeItem(TodoItem newTodoItem) {
        if(originalTodoItem == null) {
            databaseHelper.addTodoItem(newTodoItem);
        } else {
            databaseHelper.updateTodoItem(originalTodoItem, newTodoItem);
        }

    }

    public void gotoMainActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, MainActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        startActivity(showOtherActivityIntent);
    }
}
