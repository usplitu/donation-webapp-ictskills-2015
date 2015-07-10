package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Candidate;
import models.Donation;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class Report extends Controller
{

  public static void renderReport()
  {

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Report class renderReport : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      // get 5 parameters to render. States and Donors to be unique for
      // dropdowns
      // so helper methods employed. donations are what is to display in the
      // table.
      // The other lists are for the three dropdowns to filter by.
      // am also passing in user as I made an improvement to nav.html.
      // The requirement had been in story-04 to only show certain screens if
      // a user was logged in. I had previously coded this based on 'Make
      // Donation',
      // which only appeared if a user was logged in. Now, I've changed it based
      // on #{if user}
      // I've also (hopefully!) improved the UI, by putting the User name on the
      // nav bar if they
      // are on one of the three screens that appear if they're logged in, hence
      // the need to pass
      // user as a param here.

      List<Donation> donations = Donation.findAll();
      List<Candidate> candidates = Candidate.findAll();
      List<String> uniqueStates = findStates();
      List<Donation> uniqueDonations = findDonors();
      renderTemplate("Report/renderReport.html", donations, candidates, uniqueStates, uniqueDonations, user);
    }

  }

  public static void filterCandidates(String candidateEmail)
  {
    // see above in renderReport for explanation of why I am getting user and
    // passing it as a param

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Report class renderReport : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {

      // pass donations to the rendering template but first get all donations
      // and match email to the candidate email to fill donations
      // renderTemplate to renderReport.html expects the parameters to be the
      // same from every method
      List<Donation> donations = new ArrayList<Donation>();
      List<Donation> allDonations = Donation.findAll();

      // populate donations to fill the table
      for (Donation donation : allDonations)
      {
        if (donation.candidate.email.equalsIgnoreCase(candidateEmail))
        {
          donations.add(donation);
        }
      }

      // the three below need to be filled to populate the three dropdowns at
      // page
      // bottom
      List<Candidate> candidates = Candidate.findAll();
      List<String> uniqueStates = findStates();
      List<Donation> uniqueDonations = findDonors();
      renderTemplate("Report/renderReport.html", donations, candidates, uniqueStates, uniqueDonations, user);
    }
  }

  public static void filterDonors(String donorEmail)
  {
    // see above in renderReport for explanation of why I am getting user and
    // passing it as a param

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Report class renderReport : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {

      // pass donations to the rendering template but first get all donations
      // and match email to the donor email to fill donations
      // renderTemplate to renderReport.html expects the parameters to be the
      // same from every method
      List<Donation> donations = new ArrayList<Donation>();
      List<Donation> allDonations = Donation.findAll();

      // populate donations to fill the table
      for (Donation donation : allDonations)
      {
        if (donation.from.email.equalsIgnoreCase(donorEmail))
        {
          donations.add(donation);
        }
      }

      // the three below need to be filled to populate the three dropdowns at
      // page
      // bottom
      List<Candidate> candidates = Candidate.findAll();
      List<String> uniqueStates = findStates();
      List<Donation> uniqueDonations = findDonors();
      renderTemplate("Report/renderReport.html", donations, candidates, uniqueStates, uniqueDonations, user);
    }
  }

  public static void filterStates(String state)
  {
    // see above in renderReport for explanation of why I am getting user and
    // passing it as a param

    User user = Accounts.getCurrentUser();

    if (user == null)
    {
      Logger.info("Report class renderReport : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      // pass donations to the rendering template but first get all donations
      // and match state to the state parameter passed to fill donations array.
      // renderTemplate to renderReport.html expects the parameters to be the
      // same from every method
      List<Donation> donations = new ArrayList<Donation>();
      List<Donation> allDonations = Donation.findAll();

      // populate donations to fill the table
      for (Donation donation : allDonations)
      {
        if (donation.from.state.equalsIgnoreCase(state))
        {
          donations.add(donation);
        }
      }

      // the three below need to be filled to populate the three dropdowns at
      // page
      // bottom
      List<Candidate> candidates = Candidate.findAll();
      List<String> uniqueStates = findStates();
      List<Donation> uniqueDonations = findDonors();
      renderTemplate("Report/renderReport.html", donations, candidates, uniqueStates, uniqueDonations, user);
    }
  }

  private static List<String> findStates()
  {
    // return unique list of states (of Users) to calling module by first
    // putting them in HashSet
    Set<String> states = new HashSet<String>();
    List<User> users = User.findAll();

    for (User user : users)
    {
      states.add(user.state);
    }

    // creating an ArrayList of HashSet elements before returning
    List<String> listStates = new ArrayList<String>(states);
    return listStates;
  }

  private static List<Donation> findDonors()
  {
    // return unique list of donors to calling module by first
    // putting them in HashSet
    Set<String> emailSet = new HashSet<String>();
    List<Donation> listDonations = new ArrayList<Donation>();
    List<Donation> everyDonation = Donation.findAll();

    // cycle through everyDonation and if a User email is unique i.e. would be
    // added to the HashSet, then add that donation to the donation list to be
    // returned

    for (Donation donations : everyDonation)
    {
      if (emailSet.add(donations.from.email))
      {
        listDonations.add(donations);
      }
    }

    return listDonations;
  }

}
