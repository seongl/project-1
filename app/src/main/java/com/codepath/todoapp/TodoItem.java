package com.codepath.todoapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by seonglee on 2/9/17.
 */

public class TodoItem implements Serializable {
    public String text; //name
    public Date date;
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

    public TodoItem(String text, String priority) {
        super();
        this.text = text;
        this.priority = priority;
    }

    public TodoItem(String text, String priority, String status, String notes, Date dueDate) {
        super();
        this.text = text;
        this.priority = priority;
        this.status = status;
        this.notes = notes;
        this.date = dueDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((text == null) ? 0 : text.hashCode())
                + ((date == null) ? 0 : date.hashCode())
                + ((notes == null) ? 0 : text.hashCode())
                + ((priority == null) ? 0 : priority.hashCode())
                + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        final TodoItem other = (TodoItem) obj;

        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text)) {
            return false;
        }

        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date)) {
            return false;
        }

        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (!notes.equals(other.notes)) {
            return false;
        }

        if (priority == null) {
            if (other.priority != null)
                return false;
        } else if (!priority.equals(other.priority)) {
            return false;
        }

        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status)) {
            return false;
        }

        return true;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(text).append(", ")
                .append(date).append(", ")
                .append(notes).append(", ")
                .append(priority).append(", ")
                .append(status).append(", ");

        return builder.toString();
    }
}
