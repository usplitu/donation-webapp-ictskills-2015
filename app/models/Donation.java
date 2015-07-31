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

  /**
   * For donateDate, saving in this way (currentTimeMillis + Long type) and not
   * as a date format allows us more flexibility in html to use different
   * display formats. Also system does not have to take date from yml, convert
   * it and convert it again to display. Store time in millis in yml ==> more
   * efficient
   */

  public Long donateDate;

  @ManyToOne
  public User from;

  @ManyToOne
  public Candidate candidate;

  public Donation(User from, long received, String methodDonated, Candidate candidate)
  {
    this.received = received;
    this.methodDonated = methodDonated;

    // see explanation of why storing date in this format above
    this.donateDate = System.currentTimeMillis();
    this.from = from;
    this.candidate = candidate;

  }
}
