package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.db.jpa.Blob;

@Entity
public class Candidate extends Model
{
  public String firstName;
  public String lastName;
  public String email;
  public String password;

  @ManyToOne
  public Office office;

  // mapped the Admin to the Candidates and Offices they created as need to link
  // them to some tables in system.
  // In the future, more fields may be added to the Admin model, that extra
  // future functionality might require
  // allow for that possibility
  @ManyToOne
  public Admin adminBy2;

  @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
  List<Donation> donations = new ArrayList<Donation>();

  public Candidate(String firstName, String lastName, String email, String password, Office office, Admin adminBy2)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.office = office;
    this.adminBy2 = adminBy2;
  }

  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }
}
