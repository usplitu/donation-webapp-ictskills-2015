package controllers;

import java.util.ArrayList;
import java.util.List;
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
      Logger.info("Donation class renderReport : Unable to getCurrentuser");
      Accounts.login();
    }
    else
    {
      List<Donation> donations = Donation.findAll();
      List<Candidate> candidates = Candidate.findAll();

      renderTemplate("Report/renderReport.html", donations, candidates);
    }

  }

  public static void filterCandidates(String candidateEmail)
  {
    // pass donations to the rendering template but first get all donations
    // and match email to the candidate email to fill donations
    // renderTemplate to renderReport.html expects the parameters to be the
    // same from every method
    List<Donation> donations = new ArrayList<Donation>();
    List<Donation> allDonations = Donation.findAll();

    for (Donation donation : allDonations)
    {
      if (donation.from.candidate.email.equalsIgnoreCase(candidateEmail))
      {
        Logger.info("match on" + "donation email " + donation.from.candidate.email + "parameter " + candidateEmail);
        donations.add(donation);
      }
    }

    List<Candidate> candidates = Candidate.findAll();
    renderTemplate("Report/renderReport.html", donations, candidates);
  }

}
