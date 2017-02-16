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
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText etEditText;
    EditText noteEditText;

    ArrayAdapter priorityAdapter, statusAdapter;
    TodoItemDatabaseHelper databaseHelper;
    Spinner prioritySpinner, statusSpinner;
    DatePicker datePicker;

    TodoItem originalTodoItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Folks!");

        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";

                switch(item.getItemId()) {
                    case R.id.check_circle:
                        msg = "Save";
                        onSaveNewItem();
                        break;
                    case R.id.clear:
                        msg = "Clear";
                        gotoMainActivity(null);
                        break;
                }
                return false;
            }
        });

        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);

        etEditText = (EditText) findViewById(R.id.etEditText);
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        datePicker = (DatePicker) findViewById(R.id.dpResult);

        priorityAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array, R.layout.activity_add);
        statusAdapter = ArrayAdapter.createFromResource(this, R.array.status_array, R.layout.activity_add);

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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Toast.makeText(this, df.format(calendar.getTime()), Toast.LENGTH_LONG);

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
