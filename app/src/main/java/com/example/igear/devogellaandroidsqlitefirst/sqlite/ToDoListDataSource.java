package com.example.igear.devogellaandroidsqlitefirst.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TASK, taskDescription);
        values.put(MySQLiteHelper.COLUMN_PRIORITY, priority.intValue());
        long insertId = database.insert(MySQLiteHelper.TABLE_TASKS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TASKS, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ToDoItem newToDoItem = cursorToTask(cursor);
        cursor.close();
        return newToDoItem;
    }

    /*public boolean updateTask(ToDoItem taskDragged){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PRIORITY, taskDragged.getPriority());
        database.execSQL("UPDATE " + MySQLiteHelper.TABLE_TASKS + " SET " + MySQLiteHelper.COLUMN_PRIORITY
        + " = " + MySQLiteHelper.COLUMN_PRIORITY + " +1 WHERE " + MySQLiteHelper.COLUMN_PRIORITY
        + " > " + taskDragged.getPriority());
        return database.update(MySQLiteHelper.TABLE_TASKS, values, MySQLiteHelper.COLUMN_ID + "="
        + taskDragged.getId(), null) > 0;
    }*/

    public void updateDatabaseOrder(List<ToDoItem> toDoList) {
        ContentValues values = new ContentValues();
        int rowsEffected = 0;
            for (int i = 0; i < toDoList.size(); i++) {
                ToDoItem item = toDoList.get(i);
                item.setPriority(i);
                values.put(MySQLiteHelper.COLUMN_PRIORITY, i);
                rowsEffected += database.update(MySQLiteHelper.TABLE_TASKS, values, MySQLiteHelper.COLUMN_ID + " = "
                        + item.getId(), null);
            }
        Log.i("Priority Order Update", rowsEffected + " Rows were effected");
    }


    public void deleteTask(ToDoItem toDoItem){
        long id = toDoItem.getId();
        System.out.println("ToDoItem delete with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TASKS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
       /* database.execSQL("UPDATE " + MySQLiteHelper.TABLE_TASKS + " SET " + MySQLiteHelper.COLUMN_PRIORITY
                + " = " + MySQLiteHelper.COLUMN_PRIORITY + " -1 WHERE " + MySQLiteHelper.COLUMN_PRIORITY
                + " > " + toDoItem.getPriority());*/

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
        cursor.close();
        return toDoItems;
    }

    private ToDoItem cursorToTask(Cursor cursor){
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setId(cursor.getLong(0));
        toDoItem.setTask(cursor.getString(1));
        toDoItem.setPriority(cursor.getInt(2));
        return toDoItem;
    }
    public void upgradeDatabase()
    {
        dbHelper.onUpgrade(database,1,2);
    }
}
