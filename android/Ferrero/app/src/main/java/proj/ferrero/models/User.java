package proj.ferrero.models;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class User {

  private String userId;
  private String userName;
  private int load;

  private UserStatus status;

  public enum UserStatus{
    INSIDE("Inside"),
    OUTSIDE("Outside");

    String status;
    UserStatus(String strStat){
      this.status = strStat;
    }

    public String getString(){
      return status;
    }
  }


  public User(String userId, String userName, int load){
    this.userId = userId;
    this.userName = userName;
    this.load = load;

    this.status = UserStatus.OUTSIDE;

  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getLoad() {
    return load;
  }

  public void setLoad(int load) {
    this.load = load;
  }
}
