package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class DonationController extends Controller
{
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

  public static void indexRealProgress(String candidateEmail, String candidateFirstName, String candidateLastName)
  {
    // as of story-09, this section gets called instead of index() after a
    // donation as we really
    // need to call getPercentTargetAchieved and render the index.html page
    // passing in the real
    // progress and candidate name. John, you may wonder why I am passing in
    // three candidate fields
    // and not just using the Candidate object. The reason is that I kept
    // getting 'Invocation TargetException'
    // even when I had successfully accessed the Candidate object before the
    // call. I even tried this passing in User
    // with the same abend so my only option was to pass the three strings I
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
      // candidate

      String prog = getPercentTargetAchieved(candidateEmail);
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
      addDonation(user, amountDonated, methodDonated, candidate);

      // after a donation has been created, run the new method below to
      // calculate progress
      // and redisplay index.html. The reason why three strings are passed
      // instead of the
      // candidate object John, is explained in the method.
      indexRealProgress(candidateEmail, candidate.firstName, candidate.lastName);
    }

  }

  private static void addDonation(User user, long amountDonated, String methodDonated, Candidate candidate)
  {
    Donation bal = new Donation(user, amountDonated, methodDonated, candidate);
    bal.save();
  }

  private static long getDonationTarget()
  {
    return 20000;
  }

  public static String getPercentTargetAchieved(String candidateEmail)
  {
    // Just find the donations (includes the one just created) that pertain to
    // the current
    // candidate when calculating the progress %

    List<Donation> allDonations = Donation.findAll();
    long total = 0;

    for (Donation donation : allDonations)
    {
      if (donation.candidate.email.equalsIgnoreCase(candidateEmail))
      {
        total += donation.received;
      }
    }

    long target = getDonationTarget();
    long percentachieved = (total * 100 / target);
    String progress = String.valueOf(percentachieved);
    Logger.info("In getPercentTargetAchieved total " + total + " progress " + progress);
    return progress;
  }

}