package proj.ferrero.models;

/**
 * Created by mbarcelona on 1/14/16.
 */
public class User {

  private String userId;
  private String userName;
  private int load;

  private int id;
  private int age;
  private String bday;
  private String bloodType, allergies, medCond, contactPerson, contactNum;

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


 /* public User(String userId, String userName, int load){
    this.userId = userId;
    this.userName = userName;
    this.load = load;

    this.status = UserStatus.OUTSIDE;

  }*/

  public User(String userId, String userName, int load
          , int age, String bday
          , String bloodType, String allergies, String medCond
          , String contactPerson, String contactNum){
    this.userId = userId;
    this.userName = userName;
    this.load = load;

    this.status = UserStatus.OUTSIDE;

    this.bloodType = bloodType;
    this.allergies = allergies;
    this.age = age;
    this.bday = bday;
    this.medCond = medCond;
    this.contactPerson = contactPerson;
    this.contactNum = contactNum;

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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getBday() {
    return bday;
  }

  public void setBday(String bday) {
    this.bday = bday;
  }

  public String getBloodType() {
    return bloodType;
  }

  public void setBloodType(String bloodType) {
    this.bloodType = bloodType;
  }

  public String getAllergies() {
    return allergies;
  }

  public void setAllergies(String allergies) {
    this.allergies = allergies;
  }

  public String getMedCond() {
    return medCond;
  }

  public void setMedCond(String medCond) {
    this.medCond = medCond;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getContactNum() {
    return contactNum;
  }

  public void setContactNum(String contactNum) {
    this.contactNum = contactNum;
  }
}
