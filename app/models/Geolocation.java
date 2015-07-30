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
public class Geolocation extends Model
{
  public String latitude;
  public String longitude;

  // OneToMany as a Geolocation could have many users e.g. 2 users in same
  // apartment building
  @OneToMany(mappedBy = "geolocate", cascade = CascadeType.ALL)
  List<User> users = new ArrayList<User>();

  public Geolocation(String latitude, String longitude)

  {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // used in EditProfile.java where a User's state and zip has been changed
  // the lat and lng must also be changed
  public static Geolocation findById(Integer id)
  {
    return find("id", id).first();
  }
}
