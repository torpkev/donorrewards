package work.torp.donorrewards.helper;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.classes.Donation;
import work.torp.donorrewards.classes.Item;
import work.torp.donorrewards.classes.Rank;
import work.torp.donorrewards.classes.Spawner;
import work.torp.donorrewards.classes.UnclaimedCash;
import work.torp.donorrewards.classes.UnclaimedGroup;
import work.torp.donorrewards.classes.UnclaimedItem;
import work.torp.donorrewards.classes.UnclaimedMessage;
import work.torp.donorrewards.classes.UnclaimedSpawner;

public class CommandFunctions {
	public static Donation saveDonation(String uuid, String rank) {
		int donation_id = -1;
		donation_id = Main.getInstance().getDatabase().saveDonation(uuid, rank);
		
		Donation d = new Donation();
		d.setDonationID(donation_id);
		d.setUUID(uuid);
		d.setRank(rank);
		d.setDonationDateTime(new Timestamp(System.currentTimeMillis()));
		
		return d;
	}
	public static boolean saveGroups(Donation d) {
		boolean returnVal = false;
		
		if (d != null)
		{
			int group_id = Main.getInstance().getDatabase().saveGroup(d.getDonationID(), d.getUUID());
			UnclaimedGroup ug = new UnclaimedGroup();
			ug.setGroupID(group_id);
			ug.setDonationID(d.getDonationID());
			ug.setUUID(d.getUUID());
			Rank r = Provide.rankByDonationID(ug.getDonationID());
    		if (r != null)
    		{
        		ug.setGroupName(r.getAddGroups());
        		ug.setKeepGroups(r.getKeepGroups());
        		ug.setRemoveOtherGroups(r.getRemoveOtherGroups());
    		}
    		Main.getInstance().AddUnclaimedGroup(ug);
    		returnVal = true;
		}
		
		return returnVal;
	}
	public static boolean saveCash(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			int amt = 0;
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			amt = r.getCashAmount();
    			int cash_id = Main.getInstance().getDatabase().saveCash(d.getDonationID(), d.getUUID(), amt);
    			UnclaimedCash uc = new UnclaimedCash();
    			uc.setCashID(cash_id);
    			uc.setDonationID(d.getDonationID());
    			uc.setUUID(d.getUUID());
    			uc.setCashAmount(amt);
    			Main.getInstance().AddUnclaimedCash(uc);
    			returnVal = true;
    		}
		}
		
		return returnVal;
	}
	public static boolean saveItems(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			List<Item> lstI = r.getItems();
    			if (lstI != null)
    			{
    				if (lstI.size() > 0)
    				{
    					for (Item i : lstI)
    					{
    						UnclaimedItem ui = new UnclaimedItem();
    						int item_id = Main.getInstance().getDatabase().saveItem(d.getDonationID(), d.getUUID(), i.getMaterial().name(), i.getItemCount(), i.getTagItem());
    						ui.setItemID(item_id);
    						ui.setDonationID(d.getDonationID());
    						ui.setMaterial(i.getMaterial());
    						ui.setItemCount(i.getItemCount());
    						ui.setTagItem(i.getTagItem());
    						
    						Main.getInstance().AddUnclaimedItem(ui);
    						returnVal = true;
    					}
    				}
    			}
    		}
		}
		
		return returnVal;
	}
	public static boolean saveMessage(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			UnclaimedMessage um = new UnclaimedMessage();
				int player_message_id = Main.getInstance().getDatabase().saveMessage(d.getDonationID(), d.getUUID(), r.getPlayerMessage());
				um.setMessageID(player_message_id);
				um.setDonationID(d.getDonationID());
				um.setPlayerMessage(r.getPlayerMessage());
				
				Main.getInstance().AddUnclaimedMessage(um);
				returnVal = true;
    		}
		}
		
		return returnVal;
	}
	public static boolean saveSpawners(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			List<Spawner> lstS = r.getSpawners();
    			if (lstS != null)
    			{
    				if (lstS.size() > 0)
    				{
    					for (Spawner s : lstS)
    					{
    						UnclaimedSpawner us = new UnclaimedSpawner();
    						int spawner_id = Main.getInstance().getDatabase().saveSpawner(d.getDonationID(), d.getUUID(), s.getEntityType().name(), s.getSpawnerCount());
    						us.setSpawnerID(spawner_id);
    						us.setDonationID(d.getDonationID());
    						us.setEntityType(s.getEntityType());
    						us.setSpawnerCount(s.getSpawnerCount());
    						
    						Main.getInstance().AddUnclaimedSpawner(us);
    						returnVal = true;
    					}
    				}
    			}
    		}
		}
		
		return returnVal;
	}
	public static void setGroups(String uuid)
	{
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if (p != null)
		{
			List<UnclaimedGroup> lstUG = Provide.getUnclaimedGroups(uuid);
			if (lstUG != null)
			{
				for (UnclaimedGroup ug : lstUG)
				{
					if (ug.getRemoveOtherGroups())
					{
						Functions.removeGroups(p, ug.getKeepGroups());
					}
					if (ug.getGroupNames() != null)
					{
						for (String group : ug.getGroupNames())
						{
							Functions.addToGroup(p, group);
						}
					}
					Main.getInstance().getDatabase().claimGroups(ug.getGroupID(), uuid);
					Main.getInstance().RemoveUnclaimedGroup(ug);
				}
				Alert.Player("Your groups have been updated", p, true);
			}
		} else {
			Alert.Log("CommandFunctions.setGroups", "Unable to set groups for " + uuid + " - Player is offline or does not exist");
		}
	}
	public static void payPlayer(String uuid)
	{
		int totalPay = 0;
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if (p != null)
		{
			List<UnclaimedCash> lstUC = Provide.getUnclaimedCash(uuid);
			if (lstUC != null)
			{
				for (UnclaimedCash uc : lstUC)
				{
					if (uc.getCashAmount() > 0)
					{
						totalPay = totalPay + uc.getCashAmount();
						Functions.payPlayer(p, uc.getCashAmount());
						Main.getInstance().getDatabase().claimCash(uc.getCashID(), uuid);
						Main.getInstance().RemoveUnclaimedCash(uc);
					}
				}
				double newbal = Provide.getBalance(uuid);
				DecimalFormat formatter = new DecimalFormat("#0.00");        
				Alert.Player("You have been paid $" + Integer.toString(totalPay) + " - New balance = " + formatter.format(newbal), p, true);
			}
		} else {
			Alert.Log("CommandFunctions.payPlayer", "Unable to deposit funds for " + uuid + " - Player is offline or does not exist");
		}
	}
}
