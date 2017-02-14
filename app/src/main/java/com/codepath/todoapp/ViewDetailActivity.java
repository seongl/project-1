package com.codepath.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;

public class ViewDetailActivity extends AppCompatActivity {

    TextView nameDetailTextView;
    TextView dueDateDetailTextView;
    TextView notesDetailTextView;
    TextView priorityDetailTextView;
    TextView statusDetailTextView;

    TodoItem originalTodoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        originalTodoItem = (TodoItem) getIntent().getSerializableExtra(CommonConstants.fieldName);

        nameDetailTextView = (TextView) findViewById(R.id.nameDetailTextView);
        dueDateDetailTextView = (TextView) findViewById(R.id.dueDateDetailTextView);
        notesDetailTextView = (TextView) findViewById(R.id.notesDetailTextView);
        priorityDetailTextView = (TextView) findViewById(R.id.priorityDetailTextView);
        statusDetailTextView = (TextView) findViewById(R.id.statusDetailTextView);

        nameDetailTextView.setText(originalTodoItem.text);
        dueDateDetailTextView.setText(originalTodoItem.date);
        notesDetailTextView.setText(originalTodoItem.notes);
        priorityDetailTextView.setText(originalTodoItem.priority);
        statusDetailTextView.setText(originalTodoItem.status);

//        nameDetailTextView.setBackgroundColor(Color.RED);
//        dueDateDetailTextView.setBackgroundColor(Color.CYAN);
//        notesDetailTextView.setBackgroundColor(Color.YELLOW);
//        priorityDetailTextView.setBackgroundColor(Color.BLUE);
//        statusDetailTextView.setBackgroundColor(Color.GREEN);

    }
    public void onChange(View view) {
        Intent showOtherActivityIntent = new Intent(this, AddActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, originalTodoItem);
        startActivity(showOtherActivityIntent);
    }

}
