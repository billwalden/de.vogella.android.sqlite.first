package com.example.igear.devogellaandroidsqlitefirst.sqlite;

/**
 * Created by IGear on 8/10/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_ISCOMPLETE = "isComplete";
    public static final String COLUMN_COMPLETEDATE = "completeDate";

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    //sql statement
    private static final String CREATE_TABLE_TASKS = "create table "
            + TABLE_TASKS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_TASK
            + " text not null, " + COLUMN_PRIORITY
            + " int not null, " + COLUMN_ISCOMPLETE
            + " int, " + COLUMN_COMPLETEDATE
            + " int);";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println(db.getVersion() + " VERSION!!!!!!!!!");
        Cursor dbCursor =db.query(TABLE_TASKS, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        for (String name:
                columnNames) {
            System.out.println(name);
        }
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy the old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}
