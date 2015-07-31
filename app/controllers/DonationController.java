package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.json.simple.JSONObject;

import models.*;

public class DonationController extends Controller
{
  /**
   * extra security that the admin cannot get into user part of donations.
   * Accounts.getCurrentUser() checks logged_in_userid but when an admin is
   * logged in, logged_in_adminid is set in Administrator.java controller so
   * admin cannot fraudulently get in by knowing the URL.
   */

  public static void index()
  {
    /**
     * as of story-09, this method is now called only when a User clicks on the
     * 'Make Donation' tab. We don't need to calculate the percent target
     * achieved or show a candidate name on first display. The user hasn't
     * selected yet from the candidate dropdown. When they do, new method
     * indexRealProgress is called on a successful donation.
     */

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

      /**
       * want an empty progress bar first time through before donating and no
       * candidate name - fake them in donate.js! User passed as needed for name
       * display on Logout tab - see nav.html When the User is logged in, I
       * uniformly display their name on Logout tab to improve UX
       */

      render(user, candidates);

    }
  }

  public static void indexRealProgress(String candidateEmail, String candidateFirstName, String candidateLastName,
      Long candidateTarget)
  {

    /**
     * as of story-09, this section gets called instead of index() after a
     * donation as we really need to call getPercentTargetAchieved and render
     * the index.html page passing in the real progress and candidate name.
     * John, you may wonder why I am passing in individual Candidate fields and
     * not just using the Candidate object. The reason is that I kept getting
     * 'Invocation TargetException' even when I had successfully accessed the
     * Candidate object before the call. I even tried this passing in User with
     * the same abend so my only option was to pass the individual fields I
     * needed.
     */

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Donation class : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {

      /**
       * now get real values for the progress bar and candidate name for under
       * it in the label. After we have a donation, call this instead of index
       * passing in candidate fields. email is used to identify donations for
       * the candidate in question
       */

      String prog = getPercentTargetAchieved(candidateEmail, candidateTarget);
      String progress = prog + "%";
      String fullName = candidateFirstName + " " + candidateLastName;
      Logger.info("Donation ctrler : candidate is " + fullName);
      Logger.info("Donation ctrler : percent target achieved " + progress);

      /**
       * create JSON object to pass to donate.js AJAX so entire html page
       * doesn't reload
       */
      JSONObject obj = new JSONObject();
      obj.put("progress", progress);
      obj.put("fullName", fullName);
      obj.put("colorattrb", "fine");
      String value = "Your donation has been successfully registered to " + fullName + ".";
      obj.put("accepted", value);
      renderJSON(obj);

    }
  }

  public static void donate(Long amountDonated, String methodDonated, String candidateEmail)
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
      // Candidate email not user typed so no need to check lowercase
      Candidate candidate = Candidate.findByEmail(candidateEmail);

      /**
       * check if this donation will push a candidates donations past their
       * target. If so, give an error message and don't process. If not,
       * continue by adding the donation and calculating progress bar
       */

      Long total = getCandidateDonations(candidateEmail);

      if ((total + amountDonated) > candidate.donationTarget)
      {
        // get the real progress. Can't assume they're at 100%.
        String prog = getPercentTargetAchieved(candidateEmail, candidate.donationTarget);
        String progress = prog + "%";
        String fullName = candidate.firstName + " " + candidate.lastName;

        /**
         * create JSON object to pass to donate.js AJAX so entire html page
         * doesn't reload
         */

        JSONObject obj = new JSONObject();
        obj.put("progress", progress);
        obj.put("fullName", fullName);
        obj.put("colorattrb", "warning");
        String value = "Candidates donations already amount to $" + total + " . Can't accept a donation of $"
            + amountDonated + " as it would push them past their donation target of $" + candidate.donationTarget;
        obj.put("accepted", value);
        renderJSON(obj);

      }
      else
      {
        addDonation(user, amountDonated, methodDonated, candidate);

        /**
         * after a donation has been created, run the new method below to
         * calculate progress and redisplay html. The reason why individual
         * candidate fields are passed instead of the candidate object John, is
         * explained in the method.
         */

        indexRealProgress(candidateEmail, candidate.firstName, candidate.lastName, candidate.donationTarget);
      }
    }

  }

  private static void addDonation(User user, Long amountDonated, String methodDonated, Candidate candidate)
  {
    Donation bal = new Donation(user, amountDonated, methodDonated, candidate);
    bal.save();
  }

  public static Long getCandidateDonations(String candidateEmail)
  {
    /**
     * Just find the donations that pertain to the current candidate.
     * 
     */

    List<Donation> allDonations = Donation.findAll();
    Long total = 0L;

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
    /**
     * Just find the donations that pertain to the current candidate when
     * calculating the progress %. Also get the candidate Target amount
     */

    // Calculate total of all Donations for the Candidate
    Long total = getCandidateDonations(candidateEmail);
    Long target = candidateTarget;
    Long percentAchieved = (total * 100 / target);

    /**
     * if Candidate donations have exceeded their target amount, set
     * percentAchieved to 100.
     */
    percentAchieved = percentAchieved > 100 ? 100 : percentAchieved;
    String progress = String.valueOf(percentAchieved);
    Logger.info("In getPercentTargetAchieved total " + total + " progress " + progress);
    return progress;
  }

  /**
   * Create list sample positional data Necessary to add json-simple-1.1.jar or
   * equivalent to lib folder Then add jar to build path and import here
   */
  public static void listGeolocations()
  {
    // build a list of all Users in the system and their geolocations
    // then pass to map in DonationController/index.html to display as markers
    List<List<String>> jsonArray = new ArrayList<List<String>>();
    List<User> allUsers = User.findAll();
    int counter = 0;

    for (User users : allUsers)
    {
      jsonArray.add(counter, Arrays.asList(users.firstName, users.geolocate.latitude, users.geolocate.longitude));
      counter += 1;
    }

    renderJSON(jsonArray);
  }

}