package work.torp.donorrewards.classes;

import java.sql.Timestamp;

public class Donation {
	int donationID;
	String uuid;
	String rank;
	Timestamp donationDateTime;
	public int getDonationID() {
	    return donationID;
	}
	public void setDonationID(int donationID) {
		this.donationID = donationID;
	}
	public String getUUID() {
		return uuid;
	}
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
    public Timestamp getDonationDateTime() {
    	return donationDateTime;
    }
    public void setDonationDateTime(Timestamp donationDateTime) {
    	this.donationDateTime = donationDateTime;
    }
}
