package work.torp.donorrewards.classes;

public class UnclaimedMessage {
	int messageID;
	int donationID;
	String uuid;
	String playerMsg;

    public int getMessageID() {
        return messageID;
    }
    public void setMessageID(int messageID) {
    	this.messageID = messageID;
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
    public String getPlayerMessage() {
    	return playerMsg;
    }
    public void setPlayerMessage(String playerMsg) {
    	this.playerMsg = playerMsg;
    }
}
