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
  /**
   * only an admin can add an office. A logged in User should not be able to
   * access this page inadvertently by knowing the URL. In Administrator
   * Controller when an admin signs in, I wrote a key of "logged_in_adminid" to
   * the session. Check here to be safe.
   */
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
    // Title will always be saved in lowercase. Stops duplicates like
    // 'President' + 'president'
    Office duplicateOffice = Office.findByTitle(officeTitle.toLowerCase());

    /**
     * if trying to register a pre-existing office, give error message, else
     * register and display Administrator.adminReport()
     */
    if (duplicateOffice != null)
    {
      Logger.info("Attempting to register a pre-existing Office " + duplicateOffice.officeTitle + " "
          + duplicateOffice.officeDescript);

      /**
       * if Office creation unsuccessful due to a pre-existing duplicate office,
       * communicate back to AJAX call in officeIndex.js and that will redisplay
       * officeIndex.html with error
       */
      JSONObject obj = new JSONObject();
      String value = "Can't register this Office. An Office has already been registered with this title: "
          + officeTitle;
      obj.put("inputdata", value);
      renderJSON(obj);
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

        /**
         * if registration successful, communicate back to AJAX call in
         * officeIndex.js and that will handle next screen adminReport.html
         */
        JSONObject obj = new JSONObject();
        String value = "correct";
        obj.put("inputdata", value);
        renderJSON(obj);
      }
    }
  }
}
