package proj.ferrero.models;

import java.sql.Date;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class LogData {

  public enum LogType{
    USE,
    LOAD
  }


  private Date dateTime;
  private String description;
  private String userId;
  private LogType logType;


  public LogData(LogType logType, String userId, String description, Date dateTime){
    this.logType = logType;
    this.userId = userId;
    this.description = description;
    this.dateTime = dateTime;
  }


  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public LogType getLogType() {
    return logType;
  }

  public void setLogType(LogType logType) {
    this.logType = logType;
  }
}
