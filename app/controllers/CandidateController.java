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

  public static void candidIndex()
  {
    // render offices for dropdown
    List<Office> offices = Office.findAll();
    render(offices);
  }

  public static void candidRegister(String firstName, String lastName, String email, String password,
      Long donationTarget, String titleOfOffice)
  {
    // if trying to register a pre-existing candidate, give error message,
    // else register and display Administrator.adminReport()

    Candidate duplicateCand = Candidate.findByEmail(email);

    if (duplicateCand != null)
    {
      Logger.info("Attempting to register a pre-existing candidate" + duplicateCand.firstName + " "
          + duplicateCand.lastName);
      renderText("Candidate is already registered!!");
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
      {
        // get Office object to add to Candidate constructor
        Office office = Office.findByTitle(titleOfOffice);

        Logger.info(firstName + " " + lastName + " " + email + " " + password + " " + office + " " + admin);
        Candidate candidate = new Candidate(firstName, lastName, email, password, donationTarget, office, admin);
        candidate.save();
        Administrator.adminReport();
      }
    }
  }
}
