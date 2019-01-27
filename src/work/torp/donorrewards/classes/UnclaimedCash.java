package work.torp.donorrewards.classes;

public class UnclaimedCash {
	int cashID;
	int donationID;
	String uuid;
	int amt;

    public int getCashID() {
        return cashID;
    }
    public void setCashID(int cashID) {
    	this.cashID = cashID;
    }
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
    public int getCashAmount() {
        return amt;
    }
    public void setCashAmount(int amt) {
    	this.amt = amt;
    }
    
}
