package com.codepath.todoapp;

import java.io.Serializable;

/**
 * Created by seonglee on 2/9/17.
 */

public class TodoItem implements Serializable {
    public String text; //name
    public String date;
    public String notes;
    public String priority = "Low";
    public String status;

    public TodoItem() {
        super();
    }

    public TodoItem(String text){
        super();
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
