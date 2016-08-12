package com.example.igear.devogellaandroidsqlitefirst.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IGear on 8/10/2016.
 */
public class ToDoListDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TASK,
            MySQLiteHelper.COLUMN_PRIORITY};

    public ToDoListDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public ToDoItem createTask(String taskDescription, Integer priority){

        if(priority == null){
            System.out.println("priority was null!!!!");
            priority = 0;
        }
        else{
            System.out.println("priority was NOT null!!!!");
            priority = new Integer(priority.intValue() + 1);
        }

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK, taskDescription);
        values.put(MySQLiteHelper.COLUMN_PRIORITY, priority);
        long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ToDoItem newToDoItem = cursorToTask(cursor);
        cursor.close();
        return newToDoItem;
    }

    public boolean updateTask(ToDoItem taskDragged){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PRIORITY, taskDragged.getPriority());
        database.execSQL("UPDATE " + MySQLiteHelper.TABLE_TASKS + " SET " + MySQLiteHelper.COLUMN_PRIORITY
        + " = " + MySQLiteHelper.COLUMN_PRIORITY + " +1 WHERE " + MySQLiteHelper.COLUMN_PRIORITY
        + " > " + taskDragged.getPriority());
        return database.update(MySQLiteHelper.TABLE_TASKS, values, MySQLiteHelper.COLUMN_ID + "="
        + taskDragged.getId(), null) > 0;
    }

    public void deleteTask(ToDoItem toDoItem){
        long id = toDoItem.getId();
        System.out.println("ToDoItem delete with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_ID + " = " + id, null);

    }

    public List<ToDoItem> getAllTasks() {
        List<ToDoItem> toDoItems = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ToDoItem toDoItem = cursorToTask(cursor);
            toDoItems.add(toDoItem);
            cursor.moveToNext();
        }
        for (ToDoItem item:toDoItems
             ) {
            System.out.println(item.getPriority());
        }
        Collections.sort(toDoItems);
        for (ToDoItem item:toDoItems
                ) {
            System.out.println(item.getPriority());
        }
        cursor.close();
        return toDoItems;
    }

    private ToDoItem cursorToTask(Cursor cursor){
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(cursor.getLong(0));
        toDoItem.setTask(cursor.getString(1));
        return toDoItem;
    }
    public void upgradeDatabase()
    {
        dbHelper.onUpgrade(database,0,1);
    }
}
