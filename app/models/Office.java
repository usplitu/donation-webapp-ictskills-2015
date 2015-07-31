package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Office extends Model
{
  public String officeTitle;
  public String officeDescript;

  /**
   * mapped the Admin to the Candidates and Offices they created as need to link
   * them to some tables in system. In the future, more fields may be added to
   * the Admin model, that extra future functionality might require. Allow for
   * that possibility.
   */
  @ManyToOne
  public Admin adminBy1;

  @OneToMany(mappedBy = "office", cascade = CascadeType.ALL)
  public List<Candidate> candidates = new ArrayList<Candidate>();

  public Office(String officeTitle, String officeDescript, Admin adminBy1)

  {
    this.officeTitle = officeTitle.toLowerCase(); // will always be saved in
                                                  // lwrcase - facilitates find
    this.officeDescript = officeDescript;
    this.adminBy1 = adminBy1;
  }

  /**
   * used in OfficeController.java officeRegister method. When creating a new
   * office, office title is passed to method and we need to get the full Office
   * object
   */
  public static Office findByTitle(String officeTitle)
  {
    return find("officeTitle", officeTitle).first();
  }

}
