package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class DonationController extends Controller
{
  // extra security that the admin cannot get into donations.
  // Accounts.getCurrentUser()
  // checks logged_in_userid but when an admin is logged in, logged_in_adminid
  // is set
  // in Administrator.java controller
  // so admin cannot fraudulently get in by knowing the URL.
  public static void index()
  {
    // as of story-09, this method is now called only when a User clicks on the
    // 'Make Donation' tab.
    // We don't need to calculate the percent target achieved or show a
    // candidate name on first display so fake them.
    // the user hasn't selected yet from the candidate dropdown. When they do,
    // new method indexRealProgress is called.

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Donation class : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      // render candidates for dropdown
      List<Candidate> candidates = Candidate.findAll();

      // want an empty progress bar first time through before donating and no
      // candidate name - fake them!
      String progress = "0%";
      String fullName = "";
      // add new parameter of fullName to render for bar
      // so the same html page can be used when we actually donate to a
      // candidate
      render(user, candidates, progress, fullName);

    }
  }

  public static void indexRealProgress(String candidateEmail, String candidateFirstName, String candidateLastName,
      Long candidateTarget)
  {
    // as of story-09, this section gets called instead of index() after a
    // donation as we really
    // need to call getPercentTargetAchieved and render the index.html page
    // passing in the real
    // progress and candidate name. John, you may wonder why I am passing in
    // individual candidate fields
    // and not just using the Candidate object. The reason is that I kept
    // getting 'Invocation TargetException'
    // even when I had successfully accessed the Candidate object before the
    // call. I even tried this passing in User
    // with the same abend so my only option was to pass the individual fields I
    // needed.

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Donation class : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      // render candidates for dropdown
      List<Candidate> candidates = Candidate.findAll();

      // now get real values for the progress bar and candidate name for under
      // it in the label
      // after we have a donation, call this instead of index passing in
      // candidate fields. email is used to identify donations for the candidate
      // in question.

      String prog = getPercentTargetAchieved(candidateEmail, candidateTarget);
      String progress = prog + "%";
      String fullName = candidateFirstName + " " + candidateLastName;
      Logger.info("Donation ctrler : candidate is " + fullName);
      Logger.info("Donation ctrler : percent target achieved " + progress);
      // as above in index method, add parameter of fullName
      renderTemplate("DonationController/index.html", user, candidates, progress, fullName);

    }
  }

  public static void donate(long amountDonated, String methodDonated, String candidateEmail)
  {
    Logger.info("amount donated " + amountDonated + " " + "method donated " + methodDonated + " " + "candidateEmail "
        + candidateEmail);

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Donation class : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      Candidate candidate = Candidate.findByEmail(candidateEmail);

      // check if this donation will push a candidates donations past their
      // target. If so, give an error message
      // If not, continue by adding the donation and calculating progress bar.

      Long total = getCandidateDonations(candidateEmail);

      if ((total + amountDonated) > candidate.donationTarget)
      {
        renderText("Candidates donations already amount to " + total + " Can't accept a donation of " + amountDonated
            + " as it will push them past their donation target of " + candidate.donationTarget);
      }
      else
      {
        addDonation(user, amountDonated, methodDonated, candidate);

        // after a donation has been created, run the new method below to
        // calculate progress
        // and redisplay index.html. The reason why individual candidate fields
        // are passed
        // instead of the
        // candidate object John, is explained in the method.
        indexRealProgress(candidateEmail, candidate.firstName, candidate.lastName, candidate.donationTarget);
      }
    }

  }

  private static void addDonation(User user, long amountDonated, String methodDonated, Candidate candidate)
  {
    Donation bal = new Donation(user, amountDonated, methodDonated, candidate);
    bal.save();
  }

  public static Long getCandidateDonations(String candidateEmail)
  {
    // Just find the donations that pertain to
    // the current candidate. Called both when calculating values for the
    // progress bar
    // (getgetPercentTargetAchieved) and when a User is making a donation
    // (addDonation)
    // as you need to check will the Candidate exceed their Target if they
    // accept the donation

    List<Donation> allDonations = Donation.findAll();
    long total = 0;

    for (Donation donation : allDonations)
    {
      if (donation.candidate.email.equalsIgnoreCase(candidateEmail))
      {
        total += donation.received;
      }
    }

    return total;
  }

  public static String getPercentTargetAchieved(String candidateEmail, Long candidateTarget)
  {
    // Just find the donations that pertain to
    // the current candidate when calculating the progress %. Also get the
    // candidate Target
    // amount

    // Calculate total of all Donations for the Candidate
    Long total = getCandidateDonations(candidateEmail);
    long target = candidateTarget;
    long percentAchieved = (total * 100 / target);
    // if Candidate donations have exceeded their target amount, set
    // percentAchieved to 100.
    percentAchieved = percentAchieved > 100 ? 100 : percentAchieved;
    String progress = String.valueOf(percentAchieved);
    Logger.info("In getPercentTargetAchieved total " + total + " progress " + progress);
    return progress;
  }
  
  /**
   * Create list sample positional data
   * Necessary to add json-simple-1.1.jar or equivalent to lib folder
   * Then add jar to build path and import here
   */
  public static void listGeolocations()
  {
    //build a list of all Users in the system and their geolocations
    //then pass to map in DonationController/index.html to display as markers
    List<List<String>> jsonArray = new ArrayList<List<String>>();
    List<User> allUsers = User.findAll();
    int counter = 0;
    
    for (User users : allUsers)
    {
      jsonArray.add(counter, Arrays.asList(users.firstName, users.geolocate.latitude, users.geolocate.longitude));
      counter += 1;
    }
    
    
    //jsonArray.add(0, Arrays.asList("Position 1", "44.008620", "-123.074341"));
    //jsonArray.add(1, Arrays.asList("Position 2", "42.360082", "-71.058880"));
    //jsonArray.add(2, Arrays.asList("Position 3", "31.360082", "-91.058880"));
    renderJSON(jsonArray);
  }

}