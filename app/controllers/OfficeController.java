package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class OfficeController extends Controller
{
  @Before
  static void checkAuthentification()
  // only an admin can add a candidate. A logged in User should not be able to
  // access
  // this page inadvertently by knowing the URL. In Administrator Controller,
  // when an
  // admin signs in, I wrote a key of "logged_in_adminid" to the session. Check
  // here to be safe.
  {
    if (session.contains("logged_in_adminid") == false)
    {
      Administrator.login();
    }
  }

  public static void officeIndex()
  {
    render();
  }

  public static void officeRegister(String officeTitle, String officeDescript)
  {
    // if trying to register a pre-existing office, give error message,
    // else register and display Administrator.adminReport()

    Office duplicateOffice = Office.findByTitle(officeTitle);

    if (duplicateOffice != null)
    {
      Logger.info("Attempting to register a pre-existing Office" + duplicateOffice.officeTitle + " "
          + duplicateOffice.officeDescript);
      renderText("Office is already registered!!");
    }
    else
    {
      // get logged in Admin to add object to Office constructor
      Admin admin = Administrator.getCurrentAdmin();

      if (admin == null)
      {
        Logger.info("OfficeController class : Unable to getCurrentAdmin");
        Administrator.login();
      }
      else
      {
        Logger.info("created new office - title " + " " + officeTitle + " Description " + officeDescript + " Admin "
            + admin);
        Office office = new Office(officeTitle, officeDescript, admin);
        office.save();
        Administrator.adminReport();
      }
    }
  }
}
