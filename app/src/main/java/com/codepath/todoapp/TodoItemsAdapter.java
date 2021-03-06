package com.codepath.todoapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by seonglee on 2/11/17.
 */

public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    private final Context context;
    private final ArrayList<TodoItem> todoItems;

    public TodoItemsAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, -1, todoItems);
        this.context = context;
        this.todoItems = todoItems;
    }

    public ArrayList<TodoItem> getData() {
        return todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.simplerow, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.rowTextView);
        TextView priorityTextView = (TextView) rowView.findViewById(R.id.priorityTextView);

        textView.setText(todoItems.get(position).text);
        priorityTextView.setText(todoItems.get(position).priority);

        // Color the priorities
        if(CommonConstants.HIGH.equalsIgnoreCase(todoItems.get(position).priority)) {
            priorityTextView.setTextColor(Color.RED);
        } else if(CommonConstants.MED.equalsIgnoreCase(todoItems.get(position).priority)) {
            priorityTextView.setTextColor(Color.BLUE);
        } else if(CommonConstants.LOW.equalsIgnoreCase(todoItems.get(position).priority)) {
            priorityTextView.setTextColor(Color.YELLOW);
        }

        return rowView;
    }
}
