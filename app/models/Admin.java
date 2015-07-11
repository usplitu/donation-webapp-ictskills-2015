package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Admin extends Model
{
  public String username;
  public String password;
  
  
  public Admin(String username, String password)

  {
    this.username = username;
    this.password = password;    
  }

  public static Admin findByUsername(String username)
  {
    return find("username", username).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }

}
