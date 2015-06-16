package models;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.db.jpa.Blob;

@Entity
public class User extends Model
{
	public boolean usaCitizen;
	public String firstName;
	public String lastName;

	@OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
	List<Donation> donations = new ArrayList<Donation>();
	public String email;
	public String password;

	public User(boolean usaCitizen, String firstName,
			String lastName, String email, String password)
	{
		this.usaCitizen = usaCitizen;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public static User findByEmail(String email)
	{
		return find("email", email).first();
	}

	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}
}


