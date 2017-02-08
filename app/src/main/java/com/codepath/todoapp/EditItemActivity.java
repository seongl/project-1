package com.codepath.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    EditText etUpdateText;
    String originalVal;
    List<String> todoItems;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        loadData();

        originalVal = getIntent().getStringExtra(CommonConstants.fieldName);
        etUpdateText = (EditText)findViewById(R.id.etUpdateText);
        etUpdateText.setText(originalVal, TextView.BufferType.EDITABLE);
    }

    private void loadData() {
        File filesDir = getFilesDir();
        file = new File(filesDir, CommonConstants.fileName);
        try {
            todoItems = new ArrayList<>(FileUtils.readLines(file));
        } catch( IOException e ) {
            todoItems = new ArrayList<>();
        }
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
     * @param newVal
     */
    private void updateItems(String newVal) {
        // Case 1: Exit if same value is entered
        if(newVal != null && newVal.equals(originalVal)) {
            return;
        }

        // Case 2: If different value, remove and add updated value
        Iterator<String> it = todoItems.iterator();
        while( it.hasNext() ) {
            String todoItem = it.next();
            if(todoItem != null && originalVal != null &&
                    todoItem.equals(originalVal)) {
                it.remove();
                break;
            }
        }
        todoItems.add(newVal);

        try {
            FileUtils.writeLines(file, todoItems);
        } catch( IOException e ) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
