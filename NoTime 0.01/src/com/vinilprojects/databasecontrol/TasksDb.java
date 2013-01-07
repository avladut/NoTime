package com.vinilprojects.databasecontrol;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TasksDb {

public static final String KEY_ROWID = "_id";
public static final String KEY_TITLE = "title";
public static final String KEY_DETAILS = "details";
public static final String KEY_PATH = "path";
public static final String KEY_STATE = "state";
public static final String KEY_IMPORTANCE = "importance";
public static final String KEY_START = "start";
public static final String KEY_DEADLINE = "deadline";
public static final String KEY_COMPLETE = "completed";
public static final String KEY_PRIORITY ="priority";
public static final String KEY_TAGS = "tags";

private static final String LOG_TAG = "asksDb";
public static final String TABLE_NAME = "tasks";

private static final String DATABASE_CREATE =
 "CREATE TABLE if not exists " + TABLE_NAME + " (" +
  KEY_ROWID + " integer PRIMARY KEY autoincrement," +
  KEY_TITLE + "," +
  KEY_DETAILS + "," +
  KEY_PATH  + "," +
  KEY_STATE + "," +
  KEY_IMPORTANCE + "," +
  KEY_START + "," +
  KEY_DEADLINE + "," +
  KEY_COMPLETE + "," +
  KEY_PRIORITY + "," +
  KEY_TAGS + 
  ");";

public static void onCreate(SQLiteDatabase db) {
 Log.w(LOG_TAG, DATABASE_CREATE);
 db.execSQL(DATABASE_CREATE);
}

public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
   + newVersion + ", which will destroy all old data");
 db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 onCreate(db);
}
	
	
}
