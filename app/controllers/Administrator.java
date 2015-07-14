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

      // wanted to put an extra value in session logged_in_adminid to
      // distinguish an admin,
      // as a user could be logged in and type the route for admin URLs and get
      // into the restricted
      // access areas. By putting a new value in session, it can only be set if
      // an admin is logged
      // in.
      session.put("logged_in_adminid", admin.id);

      // if login successful, communicate back to AJAX call in adminlogin.js and that
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

  public static Admin getCurrentAdmin()
  {
    // get currently logged in admin for Candidate (CandidateController.java)
    // + Office (OfficeController.java) constructors via new logged_in_adminid
    // written to session
    // on admin login
    String adminId = session.get("logged_in_adminid");
    if (adminId == null)
    {
      return null;
    }
    Admin logged_in_admin = Admin.findById(Long.parseLong(adminId));
    Logger.info("In Admin controller: Logged in admin is " + logged_in_admin.username);
    return logged_in_admin;
  }

  public static void adminReport()
  {
    // only an admin can add a candidate. A logged in User should not be able to
    // access
    // this page inadvertently by knowing the URL. In Administrator Controller,
    // when an
    // admin signs in, I wrote a key of "logged_in_adminid" to the session.
    // Check here to be safe.

    if (session.contains("logged_in_adminid") == false)
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
