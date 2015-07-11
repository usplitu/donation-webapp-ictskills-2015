package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class Administrator extends Controller
{
  public static void login()
  {
    render();
  }

  public static void logout()
  {
    session.clear();
    Welcome.index();
  }

  public static void authenticate(String username, String password)
  {
    Logger.info("Attempting to authenticate with " + username + ":" + password);
    Admin admin = Admin.findByUsername(username);
    if ((admin != null) && (admin.checkPassword(password) == true))
    {
      Logger.info("Successfull authentication of " + admin.username);
      session.put("logged_in_userid", admin.id);

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

      // if login unsuccessful, communicate back to AJAX call in adminlogin.js
      // and
      // that will redisplay login.html with error
    }
  }

  public static void adminReport()
  {
    if (session.contains("logged_in_userid") == false)
    {
      Administrator.login();
    }
    else
    {
      // get lists of all Users and Candidates in the system and pass to html to
      // create tables

      List<User> users = User.findAll();
      List<Candidate> candidates = Candidate.findAll();
      render(users, candidates);
    }
  }

}
