package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etEditText;
    EditText noteEditText;

    TodoItem originalTodoItem;

    Toolbar toolbar;

    ArrayAdapter priorityAdapter, statusAdapter;
    TodoItemDatabaseHelper databaseHelper;
    Spinner prioritySpinner, statusSpinner;
    DatePicker datePicker;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.save:
                        onSaveNewItem();
                        break;
                    case R.id.clear:
                        gotoMainActivity(null);
                        break;
                }
                return false;
            }
        });

        // DB
        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        // Selected Item
        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);

        // Fields
        etEditText = (EditText) findViewById(R.id.etEditText);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        datePicker = (DatePicker) findViewById(R.id.dpResult);

        // Adapter
        priorityAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array, R.layout.activity_add);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.activity_add);

        // Edit Case
        if(originalTodoItem != null) {
            etEditText.setText(originalTodoItem.text);
            noteEditText.setText(originalTodoItem.notes);
            prioritySpinner.setSelection(priorityAdapter.getPosition(originalTodoItem.priority));
            statusSpinner.setSelection(statusAdapter.getPosition(originalTodoItem.status));

            Calendar cal = Calendar.getInstance();
            cal.setTime(originalTodoItem.date);
            datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
        }
    }

    public void onSaveNewItem() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        TodoItem newTodoItem = new TodoItem(etEditText.getText().toString(),
                prioritySpinner.getSelectedItem().toString(),
                statusSpinner.getSelectedItem().toString(),
                noteEditText.getText().toString(),
                calendar.getTime()
        );

        if(newTodoItem.equals(originalTodoItem)) {
            gotoMainActivity(newTodoItem);
        }

        etEditText.setText("");
        noteEditText.setText("");
        writeItem(newTodoItem);
        gotoMainActivity(newTodoItem);
    }

    /**
     * Insesrt or update. Depends on AddActivity or EditActivity
     *
     * @param newTodoItem
     */
    private void writeItem(TodoItem newTodoItem) {
        if(originalTodoItem == null) {
            databaseHelper.addTodoItem(newTodoItem);
        } else {
            databaseHelper.updateTodoItem(originalTodoItem, newTodoItem);
        }

    }

    public void gotoMainActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, MainActivity.class);
        if(listItem != null) {
            showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        }
        startActivity(showOtherActivityIntent);
    }
}
