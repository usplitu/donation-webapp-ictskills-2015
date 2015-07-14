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

  // mapped the Admin to the Candidates and Offices they created as need to link
  // them to some tables in system.
  // In the future, more fields may be added to the Admin model, that extra
  // future functionality might require
  // allow for that possibility. Also different tiers of Admin might be required
  // with different levels of access
  @OneToMany(mappedBy = "adminBy1", cascade = CascadeType.ALL)
  public List<Office> officeCreated = new ArrayList<Office>();

  @OneToMany(mappedBy = "adminBy2", cascade = CascadeType.ALL)
  public List<Candidate> candidateCreated = new ArrayList<Candidate>();

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
