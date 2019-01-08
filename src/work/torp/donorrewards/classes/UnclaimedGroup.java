package work.torp.donorrewards.classes;

import java.util.List;

public class UnclaimedGroup {
	int groupID;
	int donationID;
	String uuid;
	List<String> groupNames;
	boolean removeOtherGroups;
	List<String> keepGroups;
    public int getGroupID() {
        return groupID;
    }
    public void setGroupID(int groupID) {
    	this.groupID = groupID;
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
    public List<String> getGroupNames() {
    	return groupNames;
    }
    public void setGroupName(List<String> groupNames) {
    	this.groupNames = groupNames;
    }
    public boolean getRemoveOtherGroups() {
    	return removeOtherGroups;
    }
    public void setRemoveOtherGroups(boolean removeOtherGroups) {
    	this.removeOtherGroups = removeOtherGroups;
    }
    public List<String> getKeepGroups() {
    	return keepGroups;
    }
    public void setKeepGroups(List<String> keepGroups) {
    	this.keepGroups = keepGroups;
    }
}
