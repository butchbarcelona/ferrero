package proj.ferrero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import proj.ferrero.models.LogData;
import proj.ferrero.models.User;

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
  public static final String COLUMN_USERS_TAG = "tag";
  public static final String COLUMN_USERS_NAME = "name";
  public static final String COLUMN_USERS_LOAD = "load";
  public static final String COLUMN_USERS_STATUS = "status";
    public static final String COLUMN_USERS_AGE = "age";
    public static final String COLUMN_USERS_BDAY = "bday";
    public static final String COLUMN_USERS_BLOOD = "blood_type";
    public static final String COLUMN_USERS_ALLERGIES = "allergies";
    public static final String COLUMN_USERS_MED_CONDITIONS = "med_conditions";
    public static final String COLUMN_USERS_CONTACT_PERSON = "contact_person";
    public static final String COLUMN_USERS_CONTACT_NUMBER = "contact_number";


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
    + COLUMN_LOGS_DURATION + " long ,"
    + COLUMN_LOGS_FARE + " int); ";


  private static final String DATABASE_CREATE_USERS = "create table "
    + TABLE_USERS + "(" + COLUMN_USERS_ID
    + " integer primary key autoincrement, "
    + COLUMN_USERS_TAG + " text not null,"
    + COLUMN_USERS_STATUS + " text not null,"
    + COLUMN_USERS_NAME + " text not null,"
      + COLUMN_USERS_AGE + " int,"
      + COLUMN_USERS_BDAY + " text,"
      + COLUMN_USERS_BLOOD + " text,"
      + COLUMN_USERS_ALLERGIES + " text,"
      + COLUMN_USERS_MED_CONDITIONS + " text,"
      + COLUMN_USERS_CONTACT_PERSON + " text,"
      + COLUMN_USERS_CONTACT_NUMBER + " text,"
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
    values.put(COLUMN_LOGS_USER, log.getUserId());
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

  public ArrayList<LogData> getAllLogs() {
    ArrayList<LogData> logs = new ArrayList<LogData>();
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


          td.setUserName(getUser(td.getUserId()).getUserName());

        logs.add(td);
      } while (c.moveToNext());
    }
    return logs;
  }

  public ArrayList<LogData> getAllLogsOfUser(String userId) {
    ArrayList<LogData> logs = new ArrayList<LogData>();
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

  public ArrayList<LogData> getAllLogsOfUserWithoutTimeout(String userId) {
    ArrayList<LogData> logs = new ArrayList<LogData>();
    String selectQuery = "SELECT  * FROM " + TABLE_LOGS
      + " WHERE " + COLUMN_LOGS_USER + " = '" + userId+"'"
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


  public long createUser(User user) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_USERS_TAG, user.getUserId());
    values.put(COLUMN_USERS_NAME, user.getUserName());
    values.put(COLUMN_USERS_LOAD, user.getLoad());
    values.put(COLUMN_USERS_STATUS, user.getStatus().getString());
      values.put(COLUMN_USERS_AGE, user.getAge());
      values.put(COLUMN_USERS_BDAY, user.getBday());
      values.put(COLUMN_USERS_BLOOD, user.getBloodType());
      values.put(COLUMN_USERS_ALLERGIES, user.getAllergies());
      values.put(COLUMN_USERS_MED_CONDITIONS, user.getMedCond());
      values.put(COLUMN_USERS_CONTACT_PERSON, user.getContactPerson());
      values.put(COLUMN_USERS_CONTACT_NUMBER, user.getContactNum());

    // insert row
    long user_id = db.insert(TABLE_USERS, null, values);


    return user_id;
  }

  public User getUser(String userId) {
    User user = null;
    String selectQuery = "SELECT  * FROM " + TABLE_USERS
            + " WHERE " + COLUMN_USERS_ID + " = '" + userId+"'";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
    if (c.moveToFirst()) {
      user = new User((c.getString(c.getColumnIndex(COLUMN_USERS_TAG))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_NAME))),
              (c.getInt(c.getColumnIndex(COLUMN_USERS_LOAD))),
              (c.getInt(c.getColumnIndex(COLUMN_USERS_AGE))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_BDAY))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_BLOOD))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_ALLERGIES))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_MED_CONDITIONS))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_PERSON))),
              (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_NUMBER))));
    }
    return user;
  }
  public ArrayList<User> getAllUsersWithTag(String tag) {
    ArrayList<User> users = new ArrayList<User>();
    String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERS_TAG + " = '"+tag+"'";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    User user;
    // looping through all rows and adding to list
    if (c.moveToFirst()) {
      do {

        user = new User((c.getString(c.getColumnIndex(COLUMN_USERS_TAG))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_NAME))),
          (c.getInt(c.getColumnIndex(COLUMN_USERS_LOAD))),
          (c.getInt(c.getColumnIndex(COLUMN_USERS_AGE))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_BDAY))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_BLOOD))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_ALLERGIES))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_MED_CONDITIONS))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_PERSON))),
          (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_NUMBER))));
        users.add(user);
      } while (c.moveToNext());
    }
    return users;
  }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        User user;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                user = new User((c.getString(c.getColumnIndex(COLUMN_USERS_TAG))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_NAME))),
                        (c.getInt(c.getColumnIndex(COLUMN_USERS_LOAD))),
                        (c.getInt(c.getColumnIndex(COLUMN_USERS_AGE))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_BDAY))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_BLOOD))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_ALLERGIES))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_MED_CONDITIONS))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_PERSON))),
                        (c.getString(c.getColumnIndex(COLUMN_USERS_CONTACT_NUMBER))));
                users.add(user);
            } while (c.moveToNext());
        }
        return users;
    }

  public long updateUser(User user){

    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_USERS_LOAD, user.getLoad());

    // updating row
    return db.update(TABLE_USERS, values, COLUMN_USERS_TAG + " = ?",
            new String[] { String.valueOf(user.getUserId()) });
  }

  public long deleteUser(User user){
    String selectQuery = "DELETE FROM " + TABLE_USERS
      + " WHERE " + COLUMN_USERS_TAG + " = '" + user.getUserId()+"'";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor c = db.rawQuery(selectQuery, null);

    return 0;
  }
}