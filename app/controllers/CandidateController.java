package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class CandidateController extends Controller
{
  @Before
  static void checkAuthentification()
  /**
   * only an admin can add a candidate. A logged in User should not be able to
   * access this page inadvertently by knowing the URL. In Administrator
   * Controller, when an admin signs in, I wrote a key of "logged_in_adminid" to
   * the session. Check here to be safe.
   */
  {
    if (session.contains("logged_in_adminid") == false)
    {
      Administrator.login();
    }
  }

  public static void candidIndex()
  {
    // render offices for dropdown
    List<Office> offices = Office.findAll();
    render(offices);
  }

  public static void candidRegister(String firstName, String lastName, String email, String password,
      Long donationTarget, String titleOfOffice)
  {

    /**
     * if trying to register a pre-existing candidate, give error message, else
     * register and display Administrator.adminReport()
     */

    Candidate duplicateCand = Candidate.findByEmail(email.toLowerCase());
    // stops duplicate Candidate being registered in different case - all emails
    // saved in lowercase

    if (duplicateCand != null)
    {
      Logger.info("Attempting to register a pre-existing candidate" + duplicateCand.firstName + " "
          + duplicateCand.lastName);
      /**
       * if Candidate registration unsuccessful due to a pre-existing duplicate
       * candidate, communicate back to AJAX call in candidIndex.js and that
       * will redisplay candidIndex.html with error
       */
      JSONObject obj = new JSONObject();
      String value = "Can't register this Candidate. A Candidate has already been registered with the email address: "
          + duplicateCand.email;
      obj.put("inputdata", value);
      renderJSON(obj);

    }
    else
    {
      /**
       * Also check that the donation target is not an invalid one i.e. negative
       * or positive and < 500 (random minimum value) If so, communicate back to
       * AJAX call in candidIndex.js and that will redisplay candidIndex.html
       * with error. Can't handle negative values with this release of semantic
       * Also, no regular expressions allowed so handle this way.
       */

      if (donationTarget < 500)
      {
        JSONObject obj = new JSONObject();
        String value = "Invalid Donation Target. Must be at least $500. You have entered $" + donationTarget;
        obj.put("inputdata", value);
        renderJSON(obj);
      }
      else
      {

        // get logged in Admin to add object to Candidate constructor
        Admin admin = Administrator.getCurrentAdmin();

        if (admin == null)
        {
          Logger.info("CandidateController class : Unable to getCurrentAdmin");
          Administrator.login();
        }
        else
        // Register Candidate
        {
          // get Office object to add to Candidate constructor - not user typed
          // so no need to check lowercase in findByTitle
          Office office = Office.findByTitle(titleOfOffice);

          Logger.info(firstName + " " + lastName + " " + email + " " + password + " " + office + " " + admin);
          Candidate candidate = new Candidate(firstName, lastName, email, password, donationTarget, office, admin);
          candidate.save();

          /**
           * if registration successful, communicate back to AJAX call in
           * candidIndex.js and that will handle next screen adminReport.html
           */
          JSONObject obj = new JSONObject();
          String value = "correct";
          obj.put("inputdata", value);
          renderJSON(obj);
        } // if (admin == null)
      } // if (donationTarget < 500)
    } // if (duplicateCand != null)
  } // candidRegister method
}
