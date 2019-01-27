package work.torp.donorrewards.classes;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import work.torp.donorrewards.helper.Provide;

public class UnclaimedSpawner {
	int spawnerID;
	int donationID;
	String uuid;
	EntityType entityType;
	int spawnerCnt;
    public int getSpawnerID() {
        return spawnerID;
    }
    public int getDonationID() {
        return donationID;
    }
    public String getUUID() {
    	return uuid;
    }
    public EntityType getEntityType() {
        return entityType;
    }
    public int getSpawnerCount() {
        return spawnerCnt;
    }
    public void set(int spawnerID, int donationID, String uuid, EntityType entityType, int spawnerCnt)
    {
    	this.spawnerID = spawnerID;
    	this.donationID = donationID;
    	this.uuid = uuid;
    	this.entityType = entityType;
    	this.spawnerCnt = spawnerCnt;
    }
    public ItemStack getSpawner(Player player) {
    	Rank r = Provide.rankByDonationID(this.donationID);
    	ItemStack istack = Provide.getSpawner(player, this.entityType, this.spawnerID, this.spawnerCnt, true, true, r);
    	return istack;
    }
}
