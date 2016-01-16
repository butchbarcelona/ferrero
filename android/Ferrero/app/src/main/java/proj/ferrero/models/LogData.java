package proj.ferrero.models;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class LogData {

  private String userId, stationStart, stationEnd;
  private long timeIn, timeOut, duration;
  private int fare;

  private int id;

  public LogData(int id, String userId, long timeIn, String stationStart, long timeOut
    , String stationEnd, int duration, int fare ){
    this.id = id;
    this.userId = userId;
    this.timeIn = timeIn;
    this.stationStart = stationStart;

    this.timeOut = timeOut;
    this.stationEnd = stationEnd;
    this.duration = duration;
    this.fare = fare;
  }


  public LogData(int id, String userId, long timeIn, String stationStart){
    this.id = id;
    this.userId = userId;
    this.timeIn = timeIn;
    this.stationStart = stationStart;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getStationStart() {
    return stationStart;
  }

  public void setStationStart(String stationStart) {
    this.stationStart = stationStart;
  }

  public String getStationEnd() {
    return stationEnd;
  }

  public void setStationEnd(String stationEnd) {
    this.stationEnd = stationEnd;
  }

  public long getTimeIn() {
    return timeIn;
  }

  public void setTimeIn(long timeIn) {
    this.timeIn = timeIn;
  }

  public long getTimeOut() {
    return timeOut;
  }

  public void setTimeOut(long timeOut) {
    this.timeOut = timeOut;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public int getFare() {
    return fare;
  }

  public void setFare(int fare) {
    this.fare = fare;
  }
}
