package proj.ferrero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import proj.ferrero.models.LogData;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class SqlDatabaseHelper extends SQLiteOpenHelper {

  public static final String TABLE_LOGS = "logs";
  public static final String COLUMN_LOGS_ID = "_id";
  public static final String COLUMN_LOGS_USER = "user";
  public static final String COLUMN_LOGS_TIMEIN = "time_in";
  public static final String COLUMN_LOGS_TIMEOUT = "time_out";
  public static final String COLUMN_LOGS_START = "station_start";
  public static final String COLUMN_LOGS_END = "station_end";
  public static final String COLUMN_LOGS_DURATION = "duration";
  public static final String COLUMN_LOGS_FARE = "fare";


  public static final String TABLE_USERS = "users";
  public static final String COLUMN_USERS_ID = "_id";
  public static final String COLUMN_USERS_NAME = "name";
  public static final String COLUMN_USERS_LOAD = "load";
  public static final String COLUMN_USERS_STATUS = "status";


  private static final String DATABASE_NAME = "main.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE_LOGS = "create table "
    + TABLE_LOGS + "(" + COLUMN_LOGS_ID
    + " integer primary key, "
    + COLUMN_LOGS_USER + " text not null, "
    + COLUMN_LOGS_TIMEIN + " long not null, "
    + COLUMN_LOGS_TIMEOUT + " long, "
    + COLUMN_LOGS_START + " text not null, "
    + COLUMN_LOGS_END + " text, "
    + COLUMN_LOGS_DURATION + "long,"
    + COLUMN_LOGS_FARE + " int); ";

  private static final String DATABASE_CREATE_USERS = "create table "
    + TABLE_USERS + "(" + COLUMN_USERS_ID
    + " integer primary key, "
    + COLUMN_USERS_STATUS + " text not null,"
    + COLUMN_USERS_NAME + " text not null,"
    + COLUMN_USERS_LOAD + " integer not null);";

  public SqlDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE_LOGS);
    database.execSQL(DATABASE_CREATE_USERS);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(SqlDatabaseHelper.class.getName(),
      "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

    onCreate(db);
  }

  public long createLog(LogData log) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_LOGS_TIMEIN, log.getTimeIn());
    values.put(COLUMN_LOGS_TIMEOUT, log.getTimeOut());
    values.put(COLUMN_LOGS_START, log.getStationStart());
    values.put(COLUMN_LOGS_END, log.getStationEnd());
    values.put(COLUMN_LOGS_DURATION, log.getDuration());
    values.put(COLUMN_LOGS_FARE, log.getFare());

    // insert row
    long todo_id = db.insert(TABLE_LOGS, null, values);

    /*// assigning tags to todo
    for (long tag_id : tag_ids) {
      createTodoTag(todo_id, tag_id);
    }*/

    return todo_id;
  }

  public long updateLogExit(LogData log){

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_LOGS_TIMEOUT, log.getTimeOut());
    values.put(COLUMN_LOGS_END, log.getStationEnd());
    values.put(COLUMN_LOGS_DURATION, log.getDuration());
    values.put(COLUMN_LOGS_FARE, log.getFare());

    // updating row
    return db.update(TABLE_LOGS, values, COLUMN_LOGS_ID + " = ?",
      new String[] { String.valueOf(log.getId()) });
  }

  public List<LogData> getAllLogs() {
    List<LogData> logs = new ArrayList<LogData>();
    String selectQuery = "SELECT  * FROM " + TABLE_LOGS;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (c.moveToFirst()) {
      do {
        LogData td = new LogData((c.getInt(c.getColumnIndex(COLUMN_LOGS_ID)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_USER)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEIN)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_START)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEOUT)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_END)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_DURATION)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_FARE))));

        logs.add(td);
      } while (c.moveToNext());
    }
    return logs;
  }

  public List<LogData> getAllLogsOfUser(String userId) {
    List<LogData> logs = new ArrayList<LogData>();
    String selectQuery = "SELECT  * FROM " + TABLE_LOGS
      + " WHERE " + COLUMN_LOGS_USER + " = " + userId;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (c.moveToFirst()) {
      do {
        LogData td = new LogData((c.getInt(c.getColumnIndex(COLUMN_LOGS_ID)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_USER)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEIN)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_START)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEOUT)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_END)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_DURATION)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_FARE))));

        logs.add(td);
      } while (c.moveToNext());
    }
    return logs;
  }

  public List<LogData> getAllLogsOfUserWithoutTimeout(String userId) {
    List<LogData> logs = new ArrayList<LogData>();
    String selectQuery = "SELECT  * FROM " + TABLE_LOGS
      + " WHERE " + COLUMN_LOGS_USER + " = " + userId
      + " AND " + COLUMN_LOGS_END + " IS NULL";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (c.moveToFirst()) {
      do {
        LogData td = new LogData((c.getInt(c.getColumnIndex(COLUMN_LOGS_ID)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_USER)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEIN)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_START)))
          ,(c.getLong(c.getColumnIndex(COLUMN_LOGS_TIMEOUT)))
          ,(c.getString(c.getColumnIndex(COLUMN_LOGS_END)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_DURATION)))
          ,(c.getInt(c.getColumnIndex(COLUMN_LOGS_FARE))));

        logs.add(td);
      } while (c.moveToNext());
    }
    return logs;
  }
}