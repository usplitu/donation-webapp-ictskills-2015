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
    List<Candidate> candidates = Candidate.findAll();
    render(candidates);
  }

  public static void register(boolean usCitizen, String firstName, String lastName, String email, String password,
      Integer age, String state, String addr1, String addr2, String city, String zip, String candidateEmail)
  {
    Candidate candidate = Candidate.findByEmail(candidateEmail);
    Logger.info(usCitizen + " " + firstName + " " + lastName + " " + email + " " + password + " " + age + " " + state
        + " " + candidate);
    User user = new User(usCitizen, firstName, lastName, email, password, age, state, addr1, addr2, city, zip,
        candidate);
    user.save();
    login();
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

      // jg070615 commented as used JSON - DonationController.index();
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
      // jg070615 commented as used JSON - login();
      // if login unsuccessful, communicate back to AJAX call in login.js and
      // that will redisplay login.html with error
    }
  }
}
