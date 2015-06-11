package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.OneToMany;

import play.db.jpa.Model;
import play.db.jpa.Blob;

@Entity
public class Donation extends Model
{
	public long received;
	public String methodDonated;
	@ManyToOne
	public User from;

	public Donation(User from, long received, String methodDonated)
	{
		this.received = received;
		this.methodDonated = methodDonated;
		this.from = from;
	}
}

