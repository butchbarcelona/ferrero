package proj.ferrero;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class SqlDatabaseHelper extends SQLiteOpenHelper {

  public static final String TABLE_LOGS = "logs";
  public static final String COLUMN_LOGS_ID = "_id";
  public static final String COLUMN_LOGS_DESCRIPTION = "desc";
  public static final String COLUMN_LOGS_DATE = "datetime";
  public static final String COLUMN_LOGS_USER = "user";
  public static final String COLUMN_LOGS_TYPE = "type";

  public static final String TABLE_USERS = "users";
  public static final String COLUMN_USERS_ID = "_id";
  public static final String COLUMN_USERS_NAME = "name";
  public static final String COLUMN_USERS_LOAD = "load";


  private static final String DATABASE_NAME = "main.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE_LOGS = "create table "
    + TABLE_LOGS + "(" + COLUMN_LOGS_ID
    + " integer primary key autoincrement, "
    + COLUMN_LOGS_DESCRIPTION + " text not null"
    + COLUMN_LOGS_DATE + " date not null"
    + COLUMN_LOGS_USER + " text not null"
    + COLUMN_LOGS_TYPE + " text not null); ";

  private static final String DATABASE_CREATE_USERS = "create table "
    + TABLE_USERS + "(" + COLUMN_USERS_ID
    + " integer primary key autoincrement, "
    + COLUMN_LOGS_DESCRIPTION + " text not null"
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
  public long createLog(Log log) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_LOGS_DATE, );
    values.put(COLUMN_LOGS_DESCRIPTION, todo.getStatus());
    values.put(COLUMN_LOGS_TYPE, getDateTime());
    values.put(COLUMN_LOGS_USER, getDateTime());

    // insert row
    long todo_id = db.insert(TABLE_TODO, null, values);

    // assigning tags to todo
    for (long tag_id : tag_ids) {
      createTodoTag(todo_id, tag_id);
    }

    return todo_id;
  }



}