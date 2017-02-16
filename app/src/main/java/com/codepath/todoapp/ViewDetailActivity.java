package com.codepath.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ViewDetailActivity extends AppCompatActivity {

    TextView nameDetailTextView;
    TextView dueDateDetailTextView;
    TextView notesDetailTextView;
    TextView priorityDetailTextView;
    TextView statusDetailTextView;

    Toolbar toolbar;

    TodoItem originalTodoItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.edit:
                        onAddNewItem();
                        break;
                    case R.id.view_list:
                        gotoMainActivity(null);
                        break;
                }
                return false;
            }
        });

        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);

        nameDetailTextView = (TextView) findViewById(R.id.nameDetailTextView);
        dueDateDetailTextView = (TextView) findViewById(R.id.dueDateDetailTextView);
        notesDetailTextView = (TextView) findViewById(R.id.notesDetailTextView);
        priorityDetailTextView = (TextView) findViewById(R.id.priorityDetailTextView);
        statusDetailTextView = (TextView) findViewById(R.id.statusDetailTextView);

        nameDetailTextView.setText(originalTodoItem.text);
        dueDateDetailTextView.setText(new SimpleDateFormat("MM/dd/yyyy").format(originalTodoItem.date));
        notesDetailTextView.setText(originalTodoItem.notes);
        priorityDetailTextView.setText(originalTodoItem.priority);
        statusDetailTextView.setText(originalTodoItem.status);
    }

    public void onAddNewItem() {
        Intent showOtherActivityIntent = new Intent(this, AddActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) originalTodoItem);
        startActivity(showOtherActivityIntent);
    }

    public void gotoMainActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, MainActivity.class);
        if(listItem != null) {
            showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        }
        startActivity(showOtherActivityIntent);
    }

}
