package work.torp.donorrewards.classes;

import org.bukkit.Material;

public class UnclaimedItem {
	int itemID;
	int donationID;
	String uuid;
	Material material;
	int itemCnt;
	boolean tagItem;
    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
    	this.itemID = itemID;
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
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
    	this.material = material;
    }
    public int getItemCount() {
        return itemCnt;
    }
    public void setItemCount(int itemCnt) {
    	this.itemCnt = itemCnt;
    }
    public boolean getTagItem() {
    	return tagItem;
    }
    public void setTagItem(boolean tagItem) {
    	this.tagItem = tagItem;
    }
}
