package proj.ferrero.models;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class User {

  private String userId;
  private String userName;
  private int load;

  public User(String userId, String userName, int load){
    this.userId = userId;
    this.userName = userName;
    this.load = load;

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
