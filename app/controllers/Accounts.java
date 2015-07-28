package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class Accounts extends Controller
{
  public static void index()
  {
    render();
  }

  public static void signup()
  {
    render();
  }

  public static void register(boolean usCitizen, String firstName, String lastName, String email, String password,
      Integer age, String state, String addr1, String addr2, String city, String zip, String lat, String lng)
  {
    // if trying to register a pre-existing user, give error message,
    // else register and display Accounts.login()

    User duplicateUser = User.findByEmail(email);

    if (duplicateUser != null)
    {
      Logger
          .info("Attempting to register a pre-existing User" + duplicateUser.firstName + " " + duplicateUser.lastName);
      renderText("User is already registered!!");
    }
    else
    {
      Logger
          .info(usCitizen + " " + firstName + " " + lastName + " " + email + " " + password + " " + age + " " + state);

      // Get the user's geolocation, create Geolocation object, then add to User
      // constructor
      Geolocation newLocation = new Geolocation(lat, lng);
      newLocation.save();
      User user = new User(usCitizen, firstName, lastName, email, password, age, state, addr1, addr2, city, zip,
          newLocation);
      user.save();
      login();
    }
  }

  public static void login()
  {
    render();
  }

  public static void logout()
  {
    session.clear();
    index();
  }

  public static User getCurrentUser()
  {
    String userId = session.get("logged_in_userid");
    if (userId == null)
    {
      return null;
    }
    User logged_in_user = User.findById(Long.parseLong(userId));
    Logger.info("In Accounts controller: Logged in user is " + logged_in_user.firstName);
    return logged_in_user;
  }

  public static void authenticate(String email, String password)
  {
    Logger.info("Attempting to authenticate with " + email + ":" + password);
    User user = User.findByEmail(email);
    if ((user != null) && (user.checkPassword(password) == true))
    {
      Logger.info("Successfull authentication of " + user.firstName + " " + user.lastName);
      session.put("logged_in_userid", user.id);

      // if login successful, communicate back to AJAX call in login.js and that
      // will handle next screen
      JSONObject obj = new JSONObject();
      String value = "correct";
      obj.put("inputdata", value);
      renderJSON(obj);

    }
    else
    {
      Logger.info("Authentication failed");
      JSONObject obj = new JSONObject();
      String value = "Error: Incorrect Email/Password entered.";
      obj.put("inputdata", value);
      renderJSON(obj);

      // if login unsuccessful, communicate back to AJAX call in login.js and
      // that will redisplay login.html with error
    }
  }
}
