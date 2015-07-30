package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

import org.json.simple.JSONObject;

public class EditProfile extends Controller
{

  public static void profileIndex()
  {
    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("EditProfile class : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      render(user);
    }
  }

  public static void settingsChange(Long id, String firstName, String lastName, Integer age, String state,
      String addr1, String addr2, String city, String zip, String lat, String lng)
  {

    Logger.info("just in settingsChange " + firstName);
    String textString = "";
    User user = User.findById(id);

    // Check what details have changed and build a string to send to log of old
    // and new values
    if (!firstName.isEmpty())
    {
      textString += "First Name: old: " + user.firstName + " new: " + firstName;
      user.firstName = firstName;
    }

    if (!lastName.isEmpty())
    {
      textString += " Last Name: old: " + user.lastName + " new: " + lastName;
      user.lastName = lastName;
    }

    if (!state.isEmpty())
    {
      textString += " State: old: " + user.state + " new: " + state;
      user.state = state;
    }

    if (!city.isEmpty())
    {
      textString += " City: old: " + user.city + " new: " + city;
      user.city = city;
    }

    if (!addr1.isEmpty())
    {
      textString += " Addr1: old: " + user.addr1 + " new: " + addr1;
      user.addr1 = addr1;
    }

    if (!addr2.isEmpty())
    {
      textString += " Addr2: old: " + user.addr2 + " new: " + addr2;
      user.addr2 = addr2;
    }

    // zip and state must be changed in unison so get correct geolocation and
    // rewrite in this situation
    // so no need to check if zip has changed or state has changed - just one
    // will do
    if (!zip.isEmpty())
    {
      textString += " Zip: old: " + user.zip + " new: " + zip;
      user.zip = zip;
      Geolocation geolocate = Geolocation.findById(user.geolocate.id);
      geolocate.latitude = lat;
      geolocate.longitude = lng;
      geolocate.save();
    }

    if (!(age == null))
    {
      textString += " Age: old: " + user.age + " new: " + age;
      user.age = age;
    }

    // only write to log if at least 1 field has been changed i.e. textString
    // has been built or give an error message
    if (!textString.isEmpty())
    {
      user.save();
      Logger.info(user.firstName + "'s details changed " + textString);
    }
    else
    {
      renderText("No Profile Information has been changed! Press cancel from Edit Profile screen or change details");
    }

    DonationController.index();

  }
}
