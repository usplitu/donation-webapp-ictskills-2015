package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class DonorLocation extends Controller
{
  @Before
  static void checkAuthentification()
  // only an admin can add a candidate. A logged in User should not be able to
  // access this page inadvertently by knowing the URL. In Administrator Controller,
  // when an admin signs in, I wrote a key of "logged_in_adminid" to the session. Check
  // here to be safe.
  {
    if (session.contains("logged_in_adminid") == false)
    {
      Administrator.login();
    }
  }

  public static void locateIndex()
  {
    render();
  }

}
