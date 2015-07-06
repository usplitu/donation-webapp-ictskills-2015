package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

@Entity
public class User extends Model
{
  public boolean usaCitizen;
  public String firstName;
  public String lastName;
  public String email;
  public String password;
  public Integer age;
  public String state;
  public String addr1;
  public String addr2;
  public String city;
  public String zip;
  

  @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
  List<Donation> donations = new ArrayList<Donation>();
  
  

  public User(boolean usaCitizen, String firstName, String lastName, String email, String password, Integer age,
      String state, String addr1, String addr2, String city, String zip)
  {
    this.usaCitizen = usaCitizen;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.age = age;
    this.state = state;
    this.addr1 = addr1;
    this.addr2 = addr2;
    this.city  = city;
    this.zip   = zip;
        

  }

  public static User findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }
}
