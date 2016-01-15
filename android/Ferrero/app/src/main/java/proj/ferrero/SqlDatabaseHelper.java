package proj.ferrero;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    + " integer primary key autoincrement, "
    + COLUMN_LOGS_USER + " text not null "
    + COLUMN_LOGS_TIMEIN + " long not null "
    + COLUMN_LOGS_TIMEOUT + " long not null "
    + COLUMN_LOGS_START + " text not null "
    + COLUMN_LOGS_END + " text not null "
    + COLUMN_LOGS_DURATION + "long not null"
    + COLUMN_LOGS_FARE + " int not null); ";

  private static final String DATABASE_CREATE_USERS = "create table "
    + TABLE_USERS + "(" + COLUMN_USERS_ID
    + " integer primary key, "
    + COLUMN_USERS_STATUS + " text not null"
    + COLUMN_USERS_NAME + " text not null"
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

  /*
 * Creating a log
 */
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



}