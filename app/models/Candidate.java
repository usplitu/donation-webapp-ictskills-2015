package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Entity;
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

  @OneToMany(mappedBy = "candidate")
  public List<User> users;

  public Candidate(String firstName, String lastName, String email)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public static Candidate findByEmail(String email)
  {
    return find("email", email).first();
  }
}
