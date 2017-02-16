package com.codepath.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by seonglee on 2/8/17.
 */

public class TodoItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TodoItemDatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "todoItemsDatabase";
    private static final int DATABASE_VERSION = 14;

    // Table Names
    private static final String TABLE_TODOITEMS = "todoItems";

    // Post Table Columns
    private static final String KEY_TODOITEM_ID = "id";
    private static final String KEY_TODOITEM_TEXT = "text";
    private static final String KEY_TODOITEM_PRIORITY = "priority";
    private static final String KEY_TODOITEM_NOTES = "notes";
    private static final String KEY_TODOITEM_STATUS = "status";
    private static final String KEY_TODOITEM_DATE = "duedate";

    private static TodoItemDatabaseHelper sInstance;

    public static synchronized TodoItemDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoItemDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public TodoItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the tables
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_TODOITEMS +
                "(" +
                KEY_TODOITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODOITEM_TEXT + " TEXT," +
                KEY_TODOITEM_PRIORITY + " TEXT," +
                KEY_TODOITEM_STATUS + " TEXT," +
                KEY_TODOITEM_NOTES + " TEXT," +
                KEY_TODOITEM_DATE + " DATE" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // This method is called when database is upgraded like
    // modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // SQL for upgrading the tables
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOITEMS);
            onCreate(db);
        }
    }

    /*************************
     * Inserting New Records
     *************************/
    // Insert a post into the database
    public void addTodoItem(TodoItem todoItem) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODOITEM_TEXT, todoItem.text);
            values.put(KEY_TODOITEM_PRIORITY, todoItem.priority);
            values.put(KEY_TODOITEM_STATUS, todoItem.status);
            values.put(KEY_TODOITEM_NOTES, todoItem.notes);
            values.put(KEY_TODOITEM_DATE, todoItem.date.getTime());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TODOITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    /********************
     * Querying Records
     ********************/
    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();

        String TODOITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODOITEMS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODOITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TodoItem newTodoItem = new TodoItem();
                    newTodoItem.text = cursor.getString(cursor.getColumnIndex(KEY_TODOITEM_TEXT));
                    newTodoItem.priority = cursor.getString(cursor.getColumnIndex(KEY_TODOITEM_PRIORITY));
                    newTodoItem.status = cursor.getString(cursor.getColumnIndex(KEY_TODOITEM_STATUS));
                    newTodoItem.notes = cursor.getString(cursor.getColumnIndex(KEY_TODOITEM_NOTES));
                    newTodoItem.date = new Date(cursor.getLong(cursor.getColumnIndex(KEY_TODOITEM_DATE)));
                    todoItems.add(newTodoItem);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return todoItems;
    }

    /********************
     * Updating Records
     ********************/
    public int updateTodoItem(TodoItem oldTodoItem, TodoItem newTodoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODOITEM_TEXT, newTodoItem.text);
        values.put(KEY_TODOITEM_PRIORITY, newTodoItem.priority);
        values.put(KEY_TODOITEM_NOTES, newTodoItem.notes);
        values.put(KEY_TODOITEM_STATUS, newTodoItem.status);
        values.put(KEY_TODOITEM_DATE, newTodoItem.date.getTime());

        return db.update(TABLE_TODOITEMS, values, KEY_TODOITEM_TEXT + " = ?",
                new String[] { String.valueOf(oldTodoItem.text) });
    }

    /********************
     * Deleting Records
     ********************/
    public void deleteAllTodoItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODOITEMS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all todo items");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTodoItem(TodoItem todoItem) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODOITEMS, KEY_TODOITEM_TEXT + " = ?",
                    new String[] { String.valueOf(todoItem.text) });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete todo item " + todoItem.toString());
        } finally {
            db.endTransaction();
        }
    }

}