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

import play.Logger;
import play.db.jpa.Model;
import play.db.jpa.Blob;

@Entity
public class Donation extends Model
{
  public long received;
  public String methodDonated;

  // saving datetime in this way (currentTimeMillis + Long type) and not as date
  // allows us more flexibility
  // in html to use different formats. Also system does not have to take date
  // from yml and convert it to
  // time in millis as we put it in yml in this way ==> more efficient
  public Long donateDate;

  @ManyToOne
  public User from;

  @ManyToOne
  public Candidate candidate;

  public Donation(User from, long received, String methodDonated, Candidate candidate)
  {
    this.received = received;
    this.methodDonated = methodDonated;
    // saving datetime in this way (currentTimeMillis + Long type) and not as
    // date allows us more flexibility
    // in html to use different formats
    this.donateDate = System.currentTimeMillis();
    this.from = from;
    this.candidate = candidate;

  }
}
