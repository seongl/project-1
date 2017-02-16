package com.codepath.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    ArrayList<TodoItem> todoItems;
    ListView lvItems;

    TodoItemsAdapter aToDoAdapter;
    TodoItemDatabaseHelper databaseHelper;

    TodoItem selectedFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onAddNewItem(v);
//                    }
//                }
//        );

        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Folks!");



        toolbar.inflateMenu(R.menu.menu_main);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.discard:
                        if(selectedFromList != null) {
                            deleteItem(selectedFromList);
//                            lvItems.invalidateViews();

                            todoItems.remove(selectedFromList);
                            // update data in our adapter
                            aToDoAdapter.getData().remove(selectedFromList);
                            // fire the event
                            aToDoAdapter.notifyDataSetChanged();
                            selectedFromList = null;

                            turnGrey(toolbar.getMenu());
                        }
                        break;
                    case R.id.detail:
                        if(selectedFromList != null) {
                            gotoDetailItemActivity(selectedFromList);
                        }
                        break;
                    case R.id.note_add:
                        onAddNewItem();
                        break;
                    case R.id.delete_sweep:
                        databaseHelper.deleteAllTodoItems();
                        aToDoAdapter.getData().clear();
                        aToDoAdapter.notifyDataSetChanged();
                        turnGrey(toolbar.getMenu());
                        break;
                }
                return true;
            }
        });





//        toolbar.setLogo(R.drawable.good_day);
//        toolbar.setNavigationIcon(R.drawable.navigation_back);

        databaseHelper = TodoItemDatabaseHelper.getInstance(this);

        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);


//        // Long click deletes an item
//        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                TodoItem todoItem = todoItems.get(position);
//                aToDoAdapter.notifyDataSetChanged();
//                deleteItem(todoItem);
//                todoItems.remove(position);
//                return true;
//            }
//        });
//
//        // Short click goes to edit activity
//        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                gotoEditItemActivity(lvItems.getItemAtPosition(position));
//                gotoDetailItemActivity(lvItems.getItemAtPosition(position));
//            }
//        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int a = 0; a < parent.getChildCount(); a++) {
                    parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
                }

                view.setBackgroundColor(Color.GREEN);
                selectedFromList = (TodoItem) (lvItems.getItemAtPosition(position));
            }
        });
    }

    private void turnGrey(Menu menu) {
        MenuItem detailMenuItem = menu.findItem(R.id.detail);
        Drawable detailIcon = getResources().getDrawable(R.drawable.ic_detail);

        MenuItem discardMenuItem = menu.findItem(R.id.discard);
        Drawable discardIcon = getResources().getDrawable(R.drawable.ic_discard);

        MenuItem deleteSweepMenuItem = menu.findItem(R.id.delete_sweep);
        Drawable deleteSweepIcon = getResources().getDrawable(R.drawable.ic_delete_sweep);

        if(todoItems.isEmpty()) {
            detailIcon.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            detailMenuItem.setEnabled(false); // any text will be automatically disabled
            detailMenuItem.setIcon(detailIcon);

            discardIcon.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            discardMenuItem.setEnabled(false); // any text will be automatically disabled
            discardMenuItem.setIcon(discardIcon);

            deleteSweepIcon.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
            deleteSweepMenuItem.setEnabled(false); // any text will be automatically disabled
            deleteSweepMenuItem.setIcon(deleteSweepIcon);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        turnGrey(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
        String msg = "";

        switch(item.getItemId()) {
            case R.id.discard:
                msg = "Delete";
                break;
            case R.id.note_add:
                msg = "Add";
                break;
//            case R.id.edit:
//                msg = "Edit";
//                break;
//            case R.id.settings:
//                msg = "Settings";
//                break;
//            case R.id.Exit:
//                msg = "Exit";
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new TodoItemsAdapter(this, todoItems);
    }

    private void readItems() {
        todoItems = (ArrayList<TodoItem>)databaseHelper.getAllTodoItems();
    }

    private void deleteItem(TodoItem todoItem) {
        databaseHelper.deleteTodoItem(todoItem);
    }

    public void gotoEditItemActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, EditItemActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        startActivity(showOtherActivityIntent);
    }

    public void gotoDetailItemActivity(Object listItem) {
        Intent showOtherActivityIntent = new Intent(this, ViewDetailActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) listItem);
        startActivity(showOtherActivityIntent);
    }

    public void onAddNewItem() {
        Intent showOtherActivityIntent = new Intent(this, AddActivity.class);
        showOtherActivityIntent.putExtra(CommonConstants.fieldName, (Serializable) selectedFromList);
        startActivity(showOtherActivityIntent);
    }

}