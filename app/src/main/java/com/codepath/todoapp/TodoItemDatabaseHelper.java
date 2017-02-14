package com.codepath.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seonglee on 2/8/17.
 */

public class TodoItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "TodoItemDatabaseHelper";

    // Database Info
    private static final String DATABASE_NAME = "todoItemsDatabase";
    private static final int DATABASE_VERSION = 10;

    // Table Names
    private static final String TABLE_TODOITEMS = "todoItems";

    // Post Table Columns
    private static final String KEY_TODOITEM_ID = "id";
    private static final String KEY_TODOITEM_TEXT = "text";
    private static final String KEY_TODOITEM_PRIORITY = "priority";
    private static final String KEY_TODOITEM_NOTES = "notes";
    private static final String KEY_TODOITEM_STATUS = "status";
    private static final String KEY_TODOITEM_DATE = "date";

    private static TodoItemDatabaseHelper sInstance;

    public static synchronized TodoItemDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
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
    //@Override
    public void onCreate(SQLiteDatabase db) {
        // SQL for creating the tables
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_TODOITEMS +
                "(" +
                KEY_TODOITEM_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODOITEM_TEXT + " TEXT," +
                KEY_TODOITEM_PRIORITY + " TEXT," +
                KEY_TODOITEM_STATUS + " TEXT," +
                KEY_TODOITEM_NOTES + " TEXT" +
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
            // The user might already exist in the database (i.e. the same user created multiple posts).
            //long userId = addOrUpdateUser(post.user);

            ContentValues values = new ContentValues();
            values.put(KEY_TODOITEM_TEXT, todoItem.text);
            values.put(KEY_TODOITEM_PRIORITY, todoItem.priority);
            values.put(KEY_TODOITEM_STATUS, todoItem.status);
            values.put(KEY_TODOITEM_NOTES, todoItem.notes);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TODOITEMS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
//    public long addOrUpdateUser(User user) {
//        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
//        SQLiteDatabase db = getWritableDatabase();
//        long userId = -1;
//
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(KEY_USER_NAME, user.userName);
//            values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePictureUrl);
//
//            // First try to update the user in case the user already exists in the database
//            // This assumes userNames are unique
//            int rows = db.update(TABLE_USERS, values, KEY_USER_NAME + "= ?", new String[]{user.userName});
//
//            // Check if update succeeded
//            if (rows == 1) {
//                // Get the primary key of the user we just updated
//                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
//                        KEY_USER_ID, TABLE_USERS, KEY_USER_NAME);
//                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.userName)});
//                try {
//                    if (cursor.moveToFirst()) {
//                        userId = cursor.getInt(0);
//                        db.setTransactionSuccessful();
//                    }
//                } finally {
//                    if (cursor != null && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//            } else {
//                // user with this userName did not already exist, so insert new user
//                userId = db.insertOrThrow(TABLE_USERS, null, values);
//                db.setTransactionSuccessful();
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add or update user");
//        } finally {
//            db.endTransaction();
//        }
//        return userId;
//    }

    /********************
     * Querying Records
     ********************/
    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String TODOITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODOITEMS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
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
    // Update the user's profile picture url
    public int updateTodoItem(TodoItem oldTodoItem, TodoItem newTodoItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODOITEM_TEXT, newTodoItem.text);
        //values.put(KEY_TODOITEM_PRIORITY, newTodoItem.priority);
        // No need to

        // Updating profile picture url for user with that userName
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
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTodoItem(TodoItem todoItem) {
//        public int delete(String table, String whereClause, String[] whereArgs) {
//            throw new RuntimeException("Stub!");
//        }

    }

}