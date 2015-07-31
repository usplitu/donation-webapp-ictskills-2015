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
    /**
     * if trying to register a pre-existing user, give error message, else
     * register and display Accounts.login(). email always saved in lowercase in
     * Controller so won't end up with mixed case versions of same email
     * address.
     */

    User duplicateUser = User.findByEmail(email.toLowerCase());

    if (duplicateUser != null)
    {
      Logger
          .info("Attempting to register a pre-existing User" + duplicateUser.firstName + " " + duplicateUser.lastName);

      /**
       * if signup unsuccessful due to a pre-existing duplicate user,
       * communicate back to AJAX call in signup.js and that will redisplay
       * signup.html with error
       */
      JSONObject obj = new JSONObject();
      String value = "Can't signup. Someone has already registered with this email address: " + duplicateUser.email;
      obj.put("inputdata", value);
      renderJSON(obj);

    }
    else
    {
      Logger
          .info(usCitizen + " " + firstName + " " + lastName + " " + email + " " + password + " " + age + " " + state);

      /**
       * Get the user's geolocation, create Geolocation object, then add to User
       * constructor.
       */
      Geolocation newLocation = new Geolocation(lat, lng);
      newLocation.save();
      User user = new User(usCitizen, firstName, lastName, email, password, age, state, addr1, addr2, city, zip,
          newLocation);
      user.save();

      /**
       * if signup successful, communicate back to AJAX call in signup.js and
       * that will handle next screen login.html
       */
      JSONObject obj = new JSONObject();
      String value = "correct";
      obj.put("inputdata", value);
      renderJSON(obj);
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
    // no need to set email to lowercase here. User must have correct case to
    // login.
    User user = User.findByEmail(email);
    if ((user != null) && (user.checkPassword(password) == true))
    {
      Logger.info("Successfull authentication of " + user.firstName + " " + user.lastName);
      session.put("logged_in_userid", user.id);

      /**
       * if login successful, communicate back to AJAX call in login.js and that
       * will handle next screen
       */
      JSONObject obj = new JSONObject();
      String value = "correct";
      obj.put("inputdata", value);
      renderJSON(obj);

    }
    else
    {
      /**
       * if login unsuccessful, communicate back to AJAX call in login.js and
       * that will redisplay login.html with error
       */
      Logger.info("Authentication failed");
      JSONObject obj = new JSONObject();
      String value = "Error: Incorrect Email/Password entered.";
      obj.put("inputdata", value);
      renderJSON(obj);
    }
  }
}
