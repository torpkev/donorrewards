package work.torp.donorrewards.classes;

import org.bukkit.entity.EntityType;

public class UnclaimedSpawner {
	int spawnerID;
	int donationID;
	String uuid;
	EntityType entityType;
	int spawnerCnt;
    public int getSpawnerID() {
        return spawnerID;
    }
    public void setSpawnerID(int spawnerID) {
    	this.spawnerID = spawnerID;
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
    public EntityType getEntityType() {
        return entityType;
    }
    public void setEntityType(EntityType entityType) {
    	this.entityType = entityType;
    }
    public int getSpawnerCount() {
        return spawnerCnt;
    }
    public void setSpawnerCount(int spawnerCnt) {
    	this.spawnerCnt = spawnerCnt;
    }
}
